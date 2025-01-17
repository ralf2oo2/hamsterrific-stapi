package ralf2oo2.hamsterrific.entity;

import com.google.common.reflect.ClassPath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import ralf2oo2.hamsterrific.event.init.ItemRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HamsterEntity extends AnimalEntity {
    private ItemStack heldItem;
    private int stackCount;
    private int eatCount;
    private int foodStackCount;
    private int standCount;
    private ItemEntity targetFood;
    private boolean looksWithInterest;
    private boolean isStanding;
    private float field_25048_b;
    private float field_25054_c;
    private static List<String> hamsterColorList;
    PlayerEntity givemeEntity;
    private int inLove;
    private int breeding;
    private String resourceLocation;

    public HamsterEntity(World world) {
        super(world);
        this.health = 10;
        //this.standingEyeHeight = 0.2f; should be Yoffset if it exists
        this.setBoundingBoxSpacing(0.3f, 0.3f);

        this.looksWithInterest = false;
        this.heldItem = null;
        this.stackCount = 20;
        this.eatCount = 5000;
        this.standCount = 30;
        this.inLove = 0;
        this.breeding = 0;

        this.movementSpeed = 0.30000001192092896f;
        this.maxHealth = 10;

        // TODO: load texture
    }

    private String getRandomHamsterColor() {
        this.hamsterColorInitialize();
        Collections.shuffle(hamsterColorList);
        return hamsterColorList.get(0);
    }

    private void hamsterColorInitialize() {
        if (hamsterColorList == null) {
            hamsterColorList = new ArrayList<String>();
            try {
                Pattern p = Pattern.compile("assets/hamsterrific/(textures/entity/hamster/hamster_.*)");
                for (ClassPath.ResourceInfo i : ClassPath.from(this.getClass().getClassLoader()).getResources()) {
                    Matcher m = p.matcher(i.getResourceName());
                    if (!m.matches()) continue;
                    String s = m.group(1);
                    hamsterColorList.add("hamsterrific:" + s);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(16, (byte)0);
        this.dataTracker.startTracking(17, "");
        this.dataTracker.startTracking(18, "");
        this.dataTracker.startTracking(19, (byte)0);
        this.dataTracker.startTracking(20, (byte)0);
        this.dataTracker.startTracking(21, (byte)0);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("Angry", this.isHamsterAngry());
        nbt.putBoolean("Sitting", this.isHamsterSitting());
        nbt.putBoolean("InBall", this.isInBall());

        if (this.getHamsterOwner() == null) {
            nbt.putString("Owner", "");
        }
        else {
            nbt.putString("Owner", this.getHamsterOwner());
        }

        nbt.putString("Color", this.getHamsterColor());
        nbt.putInt("foodStackCount", this.getFoodStackCount());
        nbt.putInt("InLove", this.inLove);
        nbt.putInt("BallColor", this.getBallColor());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.setHamsterAngry(nbt.getBoolean("Angry"));
        this.setHamsterSitting(nbt.getBoolean("Sitting"));
        this.setInBall(nbt.getBoolean("InBall"));
        final String owner = nbt.getString("Owner");
        final String color = nbt.getString("Color");
        if (owner.length() > 0) {
            this.setHamsterOwner(owner);
            this.setHamsterTamed(true);
        }
        this.hamsterColorInitialize();
        if (color.length() > 0) {
            if (HamsterEntity.hamsterColorList.contains(color)) {
                this.setHamsterColor(color);
                this.resourceLocation = new ResourceLocation(color);
            }
            else {
                this.dead = true;
            }
        }
        this.setFoodStackCount(nbt.getInt("foodStackCount"));
        this.inLove = nbt.getInt("InLove");
        this.setBallColor(nbt.getInt("BallColor"));
    }

    @Override
    protected boolean canDespawn() {
        return !this.isHamsterTamed();
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        ItemStack itemstack = player.getHand();
        if (itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0) {
            if (true) { // TODO: originally a creative check, might add BHCREATIVE support
                --itemstack.count;
                if (itemstack.count <= 0) {
                    //player.getHand() == null; TODO: handle this case if needed
                }
            }
            this.setAttackTarget(null);
            this.setHamsterAngry(false);
            this.inLove = 600;
            this.target = null;
            for (int i = 0; i < 7; ++i) {
                final double velocityX = this.random.nextGaussian() * 0.02;
                final double velocityY = this.random.nextGaussian() * 0.02;
                final double velocityZ = this.random.nextGaussian() * 0.02;
                this.world.addParticle("heart", this.x + this.random.nextFloat() * this.width * 2.0f - this.width, this.y + 0.5 + this.random.nextFloat() * this.height, this.z + this.random.nextFloat() * this.width * 2.0f - this.width, velocityX, velocityY, velocityZ);
            }
            return true;
        }
        if (!this.isHamsterTamed()) {
            if (itemstack != null && itemstack.itemId == ItemRegistry.hamsterFoodItem.id) {
                this.addFoodStack();
                return this.interactSeedsNotTamed(itemstack, player);
            }
            return false;
        }
        else {
            if (itemstack != null && itemstack.itemId == ItemRegistry.hamsterBallItem.id && !this.isInBall()) {
                this.setInBall(true);
                this.setBallColor(itemstack.getDamage());
                itemstack.damage(1, player);
            }
            if (itemstack != null && itemstack.itemId == ItemRegistry.hamsterFoodItem.id) {
                this.addFoodStack();
                return this.interactSeedsTamed(itemstack, player);
            }
            if (itemstack == null || itemstack.itemId == Item.PAPER.id) {
                return this.interactOthersTamed();
            }
            if (this.isInBall()) {
                this.setInBall(false);
                return true;
            }
            return this.interactPaperTamed(entityplayer);
        }
    }

    private boolean interactSeedsNotTamed(ItemStack itemstack, PlayerEntity player) {
        --itemstack.count;
        if (itemstack.count <= 0) {
            //player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null); TODO: handle this
        }
        if (!this.isHamsterAngry()) {
            if (this.random.nextInt(3) == 0) {
                this.setHamsterTamed(true);
                this.setPath(null);
                this.setHamsterStanding(false);
                this.setHamsterSitting(true);
                this.setAttackTarget(null);
                this.isJumping = false;
                this.health = 10;
                this.setHamsterOwner(player.name);
                this.showHeartsOrSmokeFX("heart", 7, true);
                this.world.broadcastEntityEvent(this, (byte)7);
            }
            else {
                this.showHeartsOrSmokeFX("smoke", 7, true);
                this.world.broadcastEntityEvent(this, (byte)6);
            }
        }
        else {
            if (this.random.nextInt(3) == 0) {
                this.setHamsterAngry(false);
                this.setPath(null);
                this.isJumping = false;
                this.setTarget(null);
                this.target = null;
                this.showHeartsOrSmokeFX("note", 1, false);
                this.world.broadcastEntityEvent(this, (byte)7);
            }
            else {
                this.showHeartsOrSmokeFX("smoke", 1, true);
                this.world.broadcastEntityEvent(this, (byte)6);
            }
            this.heal(1);
        }
        return true;
    }

    void showHeartsOrSmokeFX(String name, int i, boolean flag) {
        for (int j = 0; j < i; ++j) {
            double velocityX = this.random.nextGaussian() * 0.02;
            double velocityY = this.random.nextGaussian() * 0.02;
            double velocityZ = this.random.nextGaussian() * 0.02;

            if (flag) {
                this.world.addParticle(name, this.x + (double)(this.random.nextFloat() * this.width * 2.0f) - (double)this.width, this.y + 0.5 + (double)(this.random.nextFloat() * this.height), this.z + (double)(this.random.nextFloat() * this.width * 2.0f) - (double)this.width, velocityX, velocityY, velocityZ);
                this.world.playSound(this.x + 0.5, this.y + 0.5, this.z + 0.5, "hamsterrific:hamsterrific.sound.mobs.hamster.eatsound", 3.0f, 1.0f);
                continue;
            }

            this.world.addParticle(name, this.x, this.y + 0.8, this.z, velocityX, velocityY, velocityZ);
            this.world.playSound(this.x + 0.5, this.y + 0.5, this.z + 0.5, "hamsterrific:hamsterrific.sound.mobs.hamster.eatsound", 3.0f, 1.0f);
        }
    }

    public boolean isHamsterTamed() {
        return (this.dataTracker.getByte(16) & 0x4) != 0x0;
    }

    public void setHamsterTamed(boolean flag) {
        byte tamed = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte)(tamed | 0x4));
        }
        else {
            this.dataTracker.set(16, (byte)(tamed & 0xFFFFFFFB));
        }
    }

    public String getHamsterColor() {
        return this.dataTracker.getString(18);
    }

    public void setHamsterColor(String color) {
        this.dataTracker.set(18, color);
    }

    public boolean isHamsterAngry() {
        return (this.dataTracker.getByte(16) & 0x8) != 0x0;
    }

    public void setHamsterAngry(boolean flag) {
        byte angry = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte)(angry | 0x8));
        }
        else {
            this.dataTracker.set(16, (byte)(angry & 0xFFFFFFF7));
        }
    }

    public boolean isHamsterStanding() {
        return this.isStanding;
    }

    public void setHamsterStanding(boolean flag) {
        this.isStanding = flag;
    }

    @Override
    protected int getDroppedItemId() {
        return Item.SEEDS.id;
    }

    @Override
    public ItemStack getHeldItem() {
        return this.heldItem;
    }

    public int getFoodStackCount() {
        return this.foodStackCount;
    }

    public void setFoodStackCount(final int i) {
        this.foodStackCount = i;
    }

    private boolean addFoodStack() {
        if (this.foodStackCount != 5) {
            ++this.foodStackCount;
            return true;
        }
        this.heal(1);
        return false;
    }

    private boolean eatFood() {
        if (this.foodStackCount != 0) {
            --this.foodStackCount;
            this.heal(1);
            return true;
        }
        return false;
    }

    public boolean isRidingPlayer(){
        if (this.vehicle != null) {
            if (this.vehicle instanceof PlayerEntity) {
                return true;
            }
            if (this.vehicle.vehicle != null) {
                for (Entity e = this.vehicle.vehicle; e != null; e = e.vehicle) {
                    if (e instanceof PlayerEntity) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public float isRidingSpecial() {
        float f = -1.0f;
        if (this.vehicle != null) {
            for (Entity e = this.vehicle; e != null; e = e.vehicle) {
                f = e.height;
            }
        }
        return f;
    }

    // TODO: determine wether or not this method is used
    public boolean isRidingCreature() {
        if (this.vehicle == null || this.vehicle instanceof MobEntity) {}
        return false;
    }

    public boolean isRidingHamster() {
        return this.vehicle != null && this.vehicle instanceof HamsterEntity;
    }

    @Override
    public double getPassengerRidingHeight() {
        return this.height;
    }

    @Override
    public void setTarget(Entity target) {
        super.setTarget(target);
        if (!this.isHamsterTamed() && target instanceof PlayerEntity) {
            this.setHamsterAngry(true);
        }
    }

    // TODO: Original is pumpkin seeds, find alternative
    public boolean isBreedingItem(ItemStack item) {
        return item.itemId == Item.SEEDS.id;
    }

    private void procreate(HamsterEntity hamsterEntity) {
        HamsterEntity child = (HamsterEntity) this.createChild((EntityAgeable)hamsterEntity);
        if (child != null) {
            this.setGrowingAge(6000);
            hamsterEntity.setGrowingAge(6000);
            this.inLove = 0;
            this.breeding = 0;
            this.target = null;
            hamsterEntity.target = null;
            hamsterEntity.breeding = 0;
            hamsterEntity.inLove = 0;
            child.setGrowingAge(-24000);
            child.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
            for (int i = 0; i < 7; ++i) {
                final double velocityX = this.random.nextGaussian() * 0.02;
                final double velocityY = this.random.nextGaussian() * 0.02;
                final double velocityZ = this.random.nextGaussian() * 0.02;
                this.world.addParticle("heart", this.x + this.random.nextFloat() * this.width * 2.0f - this.width, this.y + 0.5 + this.random.nextFloat() * this.height, this.z + this.random.nextFloat() * this.width * 2.0f - this.width, velocityX, velocityY, velocityZ);
            }
            this.world.spawnEntity(child);
        }
    }

    public boolean isInLove() {
        return this.inLove > 0;
    }

    public void resetInLove() {
        this.inLove = 0;
    }

    @Override
    protected String getHurtSound() {
        return "hamsterrific:hamsterrific.sound.mobs.hamster.hurtsound";
    }

    @Override
    protected String getRandomSound() {
        return "hamsterrific:hamsterrific.sound.mobs.hamster.livingsound";
    }

    @Override
    protected String getDeathSound() {
        return "hamsterrific:hamsterrific.sound.mobs.hamster.deathsound";
    }
}
