package net.fabricmc.alkimicraft.entity;

import net.fabricmc.alkimicraft.init.BlockInit;
import net.fabricmc.alkimicraft.init.EntityInit;
import net.fabricmc.alkimicraft.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;


public class CoppoiseEntity extends AnimalEntity {
    private static final TrackedData<Byte> COPPOISE_FLAGS;
    private static final int HIDING_FLAG = 1;
    private Boolean canShelling = false;
    public int shellingTime;

    public CoppoiseEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.shellingTime = this.random.nextInt(6000) + 4000;

    }

    @Override
    public boolean canSpawn(WorldView world) {
        return super.canSpawn(world);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ItemInit.COPPER_ELSHOLTZIA_SEEDS);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COPPOISE_FLAGS, (byte)0);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0D));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new TemptGoal(this, 1.25D, Ingredient.ofItems(ItemInit.COPPER_ELSHOLTZIA_SEEDS), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(5, new EscapeSunlightGoal(this, 1.25D));
        this.goalSelector.add(6, new EatCopperCropGoal(0.8, 12, 1));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && this.canShelling && --this.shellingTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 0.7F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(ItemInit.COPPER_SHELL);
            this.shellingTime = this.random.nextInt(6000) + 4000;
            this.canShelling = false;
        }
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1D).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2.0D);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.05F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    private boolean getCoppoiseFlag(int bitmask) {
        return (this.dataTracker.get(COPPOISE_FLAGS) & bitmask) != 0;
    }

    private void setCoppoiseFlags(int mask, boolean value) {
        if (value) {
            this.dataTracker.set(COPPOISE_FLAGS, (byte)(this.dataTracker.get(COPPOISE_FLAGS) | mask));
        } else {
            this.dataTracker.set(COPPOISE_FLAGS, (byte)(this.dataTracker.get(COPPOISE_FLAGS) & ~mask));
        }

    }

    public boolean isHiding() {
        return this.getCoppoiseFlag(1);
    }

    public void setHiding(boolean sitting) {
        this.setCoppoiseFlags(1, sitting);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return EntityInit.COPPOISE.create(world);
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.2F : 0.3F;
    }

    static {
        COPPOISE_FLAGS = DataTracker.registerData(CoppoiseEntity.class, TrackedDataHandlerRegistry.BYTE);

    }

    public class EatCopperCropGoal extends MoveToTargetPosGoal{
        private static final int EATING_TIME = 200;
        protected int timer;

        public EatCopperCropGoal(double speed, int range, int maxYDifference) {
            super(CoppoiseEntity.this, speed, range, maxYDifference);
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            return blockState.isOf(BlockInit.COPPER_ELSHOLTZIA) && blockState.get(CropBlock.AGE) >= 6;
        }

        public double getDesiredDistanceToTarget() {
            return 2.0D;
        }

        public boolean shouldResetPath() {
            return this.tryingTime % 100 == 0;
        }

        protected void eatCopperCrop() {
            if (CoppoiseEntity.this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                BlockState blockState = CoppoiseEntity.this.world.getBlockState(this.targetPos);
                if (blockState.isOf(BlockInit.COPPER_ELSHOLTZIA)) {
                    this.setCropState(blockState);
                    canShelling = true;
                }

            }
        }

        public void tick() {
            if (this.hasReached()) {
                if (this.timer >= EATING_TIME) {
                    this.eatCopperCrop();
                } else {
                    ++this.timer;
                }
            }

            super.tick();
        }

        private void setCropState(BlockState state) {
            CoppoiseEntity.this.playSound(SoundEvents.BLOCK_GRASS_BREAK, 1.0F, 1.0F);
            CoppoiseEntity.this.world.setBlockState(this.targetPos, BlockInit.COPPER_ELSHOLTZIA.getDefaultState().with(CropBlock.AGE, 2), 2);
        }

        public boolean canStart() {
            return !CoppoiseEntity.this.isSleeping() && super.canStart();
        }

        public void start() {
            this.timer = 0;
            CoppoiseEntity.this.setHiding(false);
            super.start();
        }
    }
}
