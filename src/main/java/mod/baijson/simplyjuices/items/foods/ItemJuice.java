package mod.baijson.simplyjuices.items.foods;

import mod.baijson.simplyjuices.SimplyJuices;
import mod.baijson.simplyjuices.items.ItemsRegistry;
import mod.baijson.skeleton.client.render.IColorAware;
import mod.baijson.skeleton.client.render.IModelAware;
import mod.baijson.skeleton.items.foods.GenericItemFood;
import mod.baijson.skeleton.items.foods.IGenericItemFood;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.awt.*;

/**
 * Created by Baijson.
 */
public class ItemJuice extends GenericItemFood implements IGenericItemFood, IModelAware, IColorAware {

    protected Color color;

    /**
     * @param resource
     * @param amount
     * @param saturation
     */
    protected ItemJuice(ResourceLocation resource, int amount, float saturation) {
        super(resource, amount, saturation);

        this.setCreativeTab(SimplyJuices.SimplyJuicesTab);
    }


    /**
     * @param resource
     * @param material
     * @param amount
     * @param saturation
     * @return
     */
    public static ItemJuice create(ResourceLocation resource, Item material, int amount, float saturation) {
        ItemJuice self = null;
        try {
            if (material instanceof ItemFood) {
                self = new ItemJuice(resource, amount, saturation);
                if (material instanceof IColorAware) {
                    self.setColor(new Color(((IColorAware) material).getColor()));
                }
            }
            if (self != null) {
                self.register();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return self;
    }

    /**
     * @param stack
     * @return
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.DRINK;
    }

    /**
     * @param stack
     * @param world
     * @param entity
     * @return
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity) {
        super.onItemUseFinish(stack, world, entity);

        if (SimplyJuices.config.enableReturnsBottle) {
            ItemStack bottle = ItemsRegistry.itemToolBottle.getDefaultInstance();
            if (entity instanceof EntityPlayer && !((EntityPlayer) entity).capabilities.isCreativeMode) {
                if (stack.isEmpty()) {
                    return bottle;
                }
                if (!((EntityPlayer) entity).inventory.addItemStackToInventory(bottle)) {
                    ((EntityPlayer) entity).dropItem(bottle, false, false);
                }
            }
        }

        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }

    /**
     * @return
     */
    @Override
    public int getColor() {
        if (this.color != null) {
            return this.color.getRGB();
        }
        return 0;
    }

    /**
     * @param value
     */
    @Override
    public void setColor(Color value) {
        this.color = value;
    }

    /**
     * @param index
     * @return
     */
    @Override
    public int getColorByIndex(int index) {
        if (index == 0) {
            return this.getColor();
        }
        return -1;
    }
}
