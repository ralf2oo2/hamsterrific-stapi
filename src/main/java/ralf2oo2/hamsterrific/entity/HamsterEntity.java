package ralf2oo2.hamsterrific.entity;

import com.google.common.reflect.ClassPath;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import ralf2oo2.hamsterrific.event.init.ItemRegistry;
import ralf2oo2.hamsterrific.mixin.LivingEntityAccessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HamsterEntity extends AgeableEntity {
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
    private String hamsterTexture;

    public HamsterEntity(World world) {
        super(world);
        this.health = 10;
        this.standingEyeHeight *= 0.5f;
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

        // TODO: check if this code works
        String texture = null;
        if (this.getHamsterColor() == "") {
            this.setHamsterColor(this.getRandomHamsterColor());
            texture = this.getHamsterColor();
            this.hamsterTexture = texture;
        }
    }

    @Override
    public String getTexture() {
        return hamsterTexture;
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
                this.hamsterTexture = color;
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
        System.out.println("interract");
        ItemStack itemstack = player.getHand();
        if (itemstack != null && this.isBreedingItem(itemstack) && this.getGrowingAge() == 0) {

            if (true) { // TODO: originally a creative check, might add BHCREATIVE support
                --itemstack.count;
                if (itemstack.count <= 0) {
                    //player.getHand() == null; TODO: handle this case if needed
                }
            }
            this.setTarget(null);
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
            if (itemstack != null && itemstack.itemId == Item.PAPER.id) {
                return this.interactPaperTamed(player);
            }
            if (this.isInBall()) {
                this.setInBall(false);
                return true;
            }
            return this.interactOthersTamed();
        }
    }

    // When player uses seeds on untamed hamster
    private boolean interactSeedsNotTamed(ItemStack itemstack, PlayerEntity player) {
        --itemstack.count;

        if (!this.isHamsterAngry()) {
            // Try to tame hamster
            if (this.random.nextInt(3) == 0) {
                this.setHamsterTamed(true);
                this.setPath(null);
                this.setHamsterStanding(false);
                this.setHamsterSitting(true);
                this.setTarget(null);
                this.jumping = false;
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
            // Try to calm down hamster
            if (this.random.nextInt(3) == 0) {
                this.setHamsterAngry(false);
                this.setPath(null);
                this.jumping = false;
                this.setTarget(null);
                this.target = null;
                this.showHeartsOrSmokeFX("note", 1, false);
                this.world.playSound(this, this.getEatSound(), this.getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
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

    // When player uses seeds on tamed hamster
    private boolean interactSeedsTamed(ItemStack itemstack, PlayerEntity player) {
        --itemstack.count;

        this.world.playSound(this, this.getEatSound(), this.getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
        this.showHeartsOrSmokeFX("note", 1, false);
        this.heal(1);
        return true;
    }
    // TODO: write code for putting down hamster gracefully
    // When player uses paper on tamed hamster
    private boolean interactPaperTamed(PlayerEntity player) {
        System.out.println("paper");
        if(player.passenger == null || player.passenger == this){
            setVehicle(player);
        }
//        else if(player.passenger == this){
//            this.vehicle = null;
//        }
        return true;
    }

    // TODO: check if this can be safely removed
    private void isRemoteMountEntity(final Entity entity) {
        if (this.vehicle == entity) {
            this.setPositionAndAngles(this.vehicle.x, this.vehicle.boundingBox.minY + this.vehicle.height, this.vehicle.z, this.yaw, this.pitch);
            this.vehicle = null;
        }
        else if (this.vehicle == null) {
            this.vehicle = entity;
            this.x = entity.x;
            this.y = entity.boundingBox.minY + this.height;
            this.z = entity.z;
            this.setPositionAndAngles(this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }

    // When player uses item that doesn't have any function on hamster
    private boolean interactOthersTamed() {
        if (this.isHamsterStanding() || !this.isHamsterSitting()) {
            this.setHamsterSitting(true);
        }
        else if (this.isHamsterSitting()) {
            this.setHamsterSitting(false);
        }
        this.jumping = false;
        this.setPath(null);
        this.setTarget(null);
        return true;
    }

    @Override
    protected void tickLiving() {
        super.tickLiving();
        if (this.targetFood != null) {
            this.actionToTargetFood();
        }
        if (this.isSubmergedInWater()) {
            this.setHamsterSitting(false);
            this.setHamsterStanding(false);
        }
        if (this.isHamsterStanding() || this.isHamsterSitting()) {
            this.movementBlocked = false;
            this.jumping = false;
            this.setPath(null);
            this.setTarget(null);
        }
    }

    private void actionToTargetFood() {
        if (!this.isMovementBlocked() && !this.hasPath() && !this.isHamsterAngry() && this.vehicle == null && this.targetFood.isAlive()) {
            final float distance = this.targetFood.getDistance(this);
            if (distance > 1.0f) {
                final Path path = this.world.findPath(this, this.targetFood, 16.0f);
                this.setPath(path);
            }
            else if (distance < 0.8f) {
                if (this.stackCount == 0) {
                    this.setPath(null);
                    this.targetFood.markDead();
                    this.targetFood = null;
                    this.addFoodStack();
                    this.showHeartsOrSmokeFX("heart", 1, false);
                    this.world.playSound(this, this.getEatSound(), this.getSoundVolume(), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                    this.stackCount = 20;
                }
                else {
                    --this.stackCount;
                }
            }
        }
        else if (!this.targetFood.isAlive()) {
            this.targetFood = null;
            this.stackCount = 20;
        }
    }

    @Override
    public void heal(int amount) {
        if(health <= 0)
        {
            return;
        }
        health += amount;
        if(health > maxHealth)
        {
            health = maxHealth;
        }
        this.hearts = this.maxHealth / 2;
    }

    @Override
    public boolean isCollidable() {
        if (this.vehicle != null && this.vehicle instanceof PlayerEntity) {
            final ItemStack itemstack = ((PlayerEntity)this.vehicle).getHand();
            if (itemstack == null || itemstack.itemId != Item.PAPER.id) {
                return false;
            }
        }
        return super.isCollidable();
    }

    @Override
    public boolean isInsideWall() {
        return this.vehicle == null && super.isInsideWall();
    }

    @Override
    public boolean damage(Entity damageSource, int amount) {
        // If hamster is riding on players head, ignore damage
        if (this.vehicle != null) {
            return false;
        }

        this.inLove = 0;
        this.setHamsterSitting(false);
        this.setHamsterStanding(false);

        // Reduce incoming damage if it is not from a player
        if (damageSource != null && !(damageSource instanceof PlayerEntity) && !(damageSource instanceof ArrowEntity)) {
            amount = (int)((amount + 1.0f) / 2.0f);
        }

        Entity lookTarget = damageSource != null ? ((LivingEntityAccessor)this).getLookTarget() : null;

        if (lookTarget != null && lookTarget instanceof PlayerEntity && this.givemeEntity == null) {
            this.givemeEntity = (PlayerEntity)lookTarget;
        }
        if (!this.isHamsterTamed() && !this.isHamsterAngry()) {
            if (lookTarget instanceof PlayerEntity) {
                this.setHamsterAngry(true);
                this.target = lookTarget;
            }

            // If damageSource is arrow, get angry at player who shot it
            if (lookTarget instanceof ArrowEntity && ((ArrowEntity)lookTarget).owner != null) {
                lookTarget = ((ArrowEntity)lookTarget).owner;
            }

            // Set surrounding hamsters to angry if hamster is untamed
            if (lookTarget instanceof LivingEntity) {
                List<HamsterEntity> list = (List<HamsterEntity>)this.world.collectEntitiesByClass(HamsterEntity.class, Box.create(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0));
                for (HamsterEntity hamster : list) {
                    if (!hamster.isHamsterTamed() && hamster.target == null) {
                        hamster.target = lookTarget;
                        if (lookTarget instanceof PlayerEntity) {
                            hamster.setHamsterAngry(true);
                        }
                    }
                }
            }
        }
        else if (lookTarget != this && lookTarget != null) {
            // If owner hit hamster, ignore. otherwise get angry
            if (this.isHamsterTamed() && lookTarget instanceof PlayerEntity && ((PlayerEntity)lookTarget).name.equalsIgnoreCase(this.getHamsterOwner())) {
                this.setTarget(null);
                this.setHamsterAngry(false);
                this.target = null;
            }
            else {
                this.target = lookTarget;
            }
        }
        return super.damage(damageSource, amount);
    }

    // TODO: figure out what this method is supposed to do, It cam probably be removed
    public boolean entityLivingBaseAttackEntityFrom(final Entity damageSource, int amount) {
//        if (this.isEntityInvulnerable()) {
//            return false;
//        }
        if (this.world.isRemote) {
            return false;
        }
        this.despawnCounter = 0;
        if (this.health <= 0) {
            return false;
        }
        // TODO: incompatible with current version, think about what to do with it
//        if (par1DamageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
//            return false;
//        }
//        if (par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) {
//            distance *= 0.75f;
//        }
        this.swingAnimationProgress = 1.5f;
        boolean var3 = true;
        if (this.hurtTime > this.damagedTime / 2) {
            //TODO: damageAmount might be wrong here
            if (amount <= damageAmount) {
                return false;
            }
            this.applyDamage((int)(amount - this.damageAmount));
            this.damageAmount = amount;
            var3 = false;
        }
        else {
            this.damageAmount = amount;
            this.prevHealth = this.health;
            this.hurtTime = this.damagedTime;
            this.applyDamage(amount);
            // TODO: check for this
            //this.maxHurtTime = 10;
            this.hurtTime = 10;
        }
        this.damagedSwingDir = 0.0f;

        if (damageSource != null) {
            if (damageSource instanceof LivingEntity) {
                // TODO: implement this code
                //this.setRevengeTarget((EntityLivingBase)var4);
            }
            //TODO find out what recentlyhit does
            if (damageSource instanceof PlayerEntity) {
                //this.recentlyHit = 100;
                this.target = (PlayerEntity)damageSource;
            }
            else if (damageSource instanceof WolfEntity) {
                final WolfEntity wolf = (WolfEntity) damageSource;
                if (wolf.isTamed()) {
                    //this.recentlyHit = 100;
                    this.target = null;
                }
            }
        }
        if (var3) {
            this.world.broadcastEntityEvent(this, (byte)2);
//            if (par1DamageSource != DamageSource.drown) {
//                this.setBeenAttacked();
//            }
            if (damageSource != null) {
                double var6;
                double var7;
                for (var6 = damageSource.x - this.x, var7 = damageSource.z - this.z; var6 * var6 + var7 * var7 < 1.0E-4; var6 = (Math.random() - Math.random()) * 0.01, var7 = (Math.random() - Math.random()) * 0.01) {}
                this.damagedSwingDir = (float)(Math.atan2(var7, var6) * 180.0 / 3.141592653589793) - this.yaw;
                this.applyKnockback(damageSource, amount, var6, var7);
            }
            else {
                this.damagedSwingDir = (float)((int)(Math.random() * 2.0) * 180);
            }
        }
        if (this.health <= 0) {
            if (var3) {
                this.world.playSound(this, this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());
            }

            // TODO: I don't think this is supported, replacing it with markDead for now
            this.onKilledBy(damageSource);
        }
        else if (var3) {
            this.world.playSound(this, this.getHurtSound(), this.getSoundVolume(), this.getSoundPitch());
        }
        return true;
    }

    @Override
    protected Entity getTargetInRange() {
        final Box expandedBoundingBox = this.boundingBox.expand(8.0, 8.0, 8.0);

        // Look for other hamster in love to procreate
        if (this.inLove > 0) {
            final List<HamsterEntity> hamstersInVincinity = (List<HamsterEntity>)this.world.collectEntitiesByClass(this.getClass(), expandedBoundingBox);
            for (HamsterEntity hamsterCandidate : hamstersInVincinity) {
                if (hamsterCandidate != this && hamsterCandidate.inLove > 0) {
                    this.setTarget(null);
                    return hamsterCandidate;
                }
            }
        }

        // Go after player holding food item
        else if (this.getGrowingAge() == 0 && !this.isHamsterStanding() && !this.isHamsterSitting()) {
            final List<PlayerEntity> playersInVincinity = (List<PlayerEntity>)this.world.collectEntitiesByClass(PlayerEntity.class, expandedBoundingBox);
            for (final PlayerEntity playerCandidate : playersInVincinity) {
                if (playerCandidate.getHand() != null && this.isBreedingItem(playerCandidate.getHand())) {
                    this.setTarget(null);
                    this.setHamsterAngry(false);
                    return playerCandidate;
                }
            }
        }

        // Stay with child hamster TODO: confirm if this is correct
        else if (this.getGrowingAge() > 0 && !this.isHamsterStanding() && !this.isHamsterSitting()) {
            final List<HamsterEntity> hamstersInVincinity = (List<HamsterEntity>)this.world.collectEntitiesByClass(this.getClass(), expandedBoundingBox);
            for (final HamsterEntity hamsterCandidate : hamstersInVincinity) {
                if (hamsterCandidate != this && hamsterCandidate.getGrowingAge() < 0) {
                    this.setTarget(null);
                    this.setHamsterAngry(false);
                    this.target = null;
                    return hamsterCandidate;
                }
            }
        }
        if (this.isHamsterAngry()) {
            return this.world.getClosestPlayer(this, 16.0);
        }
        return null;
    }

    @Override
    protected void attack(Entity other, float distance) {
        if (other instanceof PlayerEntity player) {
            if (distance < 3.0f) {
                final double d = other.x - this.x;
                final double d2 = other.x - this.x;
                this.yaw = (float)(Math.atan2(d2, d) * 180.0 / 3.141592653589793) - 90.0f;
                this.movementBlocked = true;
//                this.isStanding = true;
//                this.jump();
            }
            if (player.getHand() == null || !this.isBreedingItem(player.getHand())) {
                this.target = null;
            }
        }
        else if (other instanceof HamsterEntity) {
            HamsterEntity hamster = (HamsterEntity) other;
            if (this.getGrowingAge() > 0 && hamster.getGrowingAge() < 0) {
                if (distance < 2.5) {
                    this.movementBlocked = true;
                }
            }
            else if (this.inLove > 0 && hamster.inLove > 0) {
                if (hamster.target == null) {
                    hamster.target = this;
                }
                if (hamster.target == this && distance < 3.5) {
                    ++hamster.inLove;
                    ++this.inLove;
                    ++this.breeding;
                    if (this.breeding % 4 == 0) {
                        this.world.addParticle("heart", this.x + this.random.nextFloat() * this.width * 2.0f - this.width, this.y + 0.5 + this.random.nextFloat() * this.height, this.z + this.random.nextFloat() * this.width * 2.0f - this.width, 0.0, 0.0, 0.0);
                    }
                    if (this.breeding == 10) {
                        this.procreate((HamsterEntity)other);
                    }
                }
                else {
                    this.breeding = 0;
                }
            }
            else {
                this.breeding = 0;
                this.target = null;
            }
        }
        if (distance > 2.0f && distance < 6.0f && this.random.nextInt(10) == 0) {
            if (this.onGround) {
                final double distanceX = other.x - this.x;
                final double distanceZ = other.z - this.z;
                final float f2 = MathHelper.sqrt(distanceX * distanceX + distanceZ * distanceZ);
                this.velocityX = distanceX / f2 * 0.5 * 0.800000011920929 + this.velocityX * 0.20000000298023224;
                this.velocityZ = distanceZ / f2 * 0.5 * 0.800000011920929 + this.velocityZ * 0.20000000298023224;
                this.velocityY = 0.4000000059604645;
            }
        }
        else if (!(other instanceof HamsterEntity) && (!(other instanceof PlayerEntity) || !((PlayerEntity)other).name.equalsIgnoreCase(this.getHamsterOwner())) && distance < 1.5 && other.boundingBox.maxY > this.boundingBox.minY && other.boundingBox.minY < this.boundingBox.maxY) {
            this.attackCooldown = 20;
            other.damage(this, 1);
        }
    }

    @Override
    protected void jump() {
        this.velocityY = 0.3;
    }


    // TODO: inlove counter needs to be handled here or in AgeableEntity
    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.isHamsterAngry()) {
            this.inLove = 0;
        }
        super.tickMovement();
        if (this.health < 10) {
            this.eatFood();
            this.eatCount = 5000;
        }
        if (!this.isHamsterStanding() && !this.isHamsterSitting()) {
            if (this.random.nextInt(20) == 0 && this.random.nextInt(20) == 0) {
                this.setHamsterStanding(true);
                this.standCount = 30;
                this.setPath(null);
                this.jumping = false;
            }
        }
        else if (this.isHamsterStanding() && this.standCount-- <= 0 && this.random.nextInt(10) == 0) {
            this.setHamsterStanding(false);
        }
        if (this.getFoodStackCount() > 0) {
            if (this.eatCount == 0) {
                if (this.random.nextInt(30) == 0 && this.random.nextInt(30) == 0) {
                    this.eatFood();
                    this.eatCount = 5000;
                }
            }
            else {
                this.eatCount--;
            }
        }
        this.looksWithInterest = false;
        if (!this.hasPath() && !this.isHamsterAngry()) {
            // TODO: check if this is correct
            Entity entity = this.getLookTarget();
            if (entity instanceof PlayerEntity player) {
                ItemStack itemstack = player.getHand();
                if (itemstack != null && itemstack.itemId == Item.SEEDS.id) {
                    this.looksWithInterest = true;
                }
            }
        }
        if (this.targetFood == null) {
            final List<Entity> loadedEntities = (List<Entity>)this.world.entities;
            for (Entity loadedEntity : loadedEntities) {
                if (!(loadedEntity instanceof ItemEntity)) {
                    continue;
                }
                final ItemEntity itemEntity = (ItemEntity) loadedEntity;
                final ItemStack item = itemEntity.stack;
                if (item == null || item.itemId != Item.SEEDS.id) {
                    continue;
                }
                if (itemEntity.getDistance(this) >= 5.0f) {
                    continue;
                }
                this.targetFood = itemEntity;
            }
        }
        // TODO: I think this needs to setpath, not settarget
        if ((this.isHamsterSitting() | this.isHamsterStanding()) && this.hasPath()) {
            this.setTarget(null);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.field_25054_c = this.field_25048_b;
        if (this.looksWithInterest) {
            this.field_25048_b += (1.0f - this.field_25048_b) * 0.4f;
        }
        else {
            this.field_25048_b += (0.0f - this.field_25048_b) * 0.4f;
        }
        if (this.looksWithInterest) {
            this.lookTimer = 10;
        }
        if (this.random.nextInt(10) == 5) {
            this.age++;
        }
    }

    public float getInterestedAngle(final float f) {
        return (this.field_25054_c + (this.field_25048_b - this.field_25054_c) * f) * 0.15f * 3.141593f;
    }

    // TODO: check if movementblocked should be here
    @Override
    protected boolean isMovementBlocked() {
        return this.isHamsterSitting() || this.isHamsterStanding() || this.movementBlocked;
    }

    public String getHamsterOwner() {
        return this.dataTracker.getString(17);
    }

    public void setHamsterOwner(String owner) {
        this.dataTracker.set(17, owner);
    }

    public boolean isInBall() {
        return this.dataTracker.getByte(20) == 1;
    }

    public void setInBall(boolean inBall) {
        this.dataTracker.set(20, (byte)(inBall ? 1 : 0));
    }

    public int getBallColor() {
        return this.dataTracker.getByte(21);
    }

    public void setBallColor(int color) {
        this.dataTracker.set(21, (byte)color);
    }

    public boolean isHamsterSitting() {
        return (this.dataTracker.getByte(16) & 1) != 0;
    }

    public void setHamsterSitting(boolean flag) {
        byte hamsterSitting = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte)(hamsterSitting | 1));
        }
        else {
            this.dataTracker.set(16, (byte)(hamsterSitting & -2));
        }
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
        return (this.dataTracker.getByte(16) & 4) != 0;
    }

    public void setHamsterTamed(boolean flag) {
        byte tamed = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte)(tamed | 4));
        }
        else {
            this.dataTracker.set(16, (byte)(tamed & -5));
        }
    }

    public String getHamsterColor() {
        return this.dataTracker.getString(18);
    }

    public void setHamsterColor(String color) {
        this.dataTracker.set(18, color);
    }

    public boolean isHamsterAngry() {
        return (this.dataTracker.getByte(16) & 8) != 0;
    }

    public void setHamsterAngry(boolean flag) {
        if(flag){
            System.out.println("Hamster is mad");
        }
        else{
            System.out.println("Hamster is not mad");
        }
        byte angry = this.dataTracker.getByte(16);
        if (flag) {
            this.dataTracker.set(16, (byte)(angry | 8));
        }
        else {
            this.dataTracker.set(16, (byte)(angry & -9));
        }
        System.out.println(isHamsterAngry());
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
            this.foodStackCount++;
            return true;
        }
        this.heal(1);
        return false;
    }

    private boolean eatFood() {
        if (this.foodStackCount != 0) {
            this.foodStackCount--;
            this.heal(1);
            return true;
        }
        return false;
    }

    public HamsterEntity createChild(HamsterEntity hamster) {
        HamsterEntity child = new HamsterEntity(this.world);

        if (hamster.isHamsterTamed()) {
            child.setHamsterTamed(true);
            child.setHamsterOwner(hamster.getHamsterOwner());
        }

        child.setHamsterColor(hamster.getHamsterColor());
        child.hamsterTexture = hamster.hamsterTexture;
        return child;
    }

    // TODO: can probably remove this and use hasVehicle
    public boolean isRiding() {
        return this.vehicle != null;
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
        return this.height * 0.75;
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
        HamsterEntity child = this.createChild(hamsterEntity);
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

    protected String getEatSound(){
        return "hamsterrific:hamsterrific.sound.mobs.hamster.eatsound";
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
