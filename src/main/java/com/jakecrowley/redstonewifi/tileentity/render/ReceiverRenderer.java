package com.jakecrowley.redstonewifi.tileentity.render;

import com.jakecrowley.redstonewifi.tileentity.TileEntityReceiver;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class ReceiverRenderer extends TileEntitySpecialRenderer<TileEntityReceiver> {

    @Override
    public void render(TileEntityReceiver te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(0, -0.50, 0);
            super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        }
        GlStateManager.popMatrix();
    }

}
