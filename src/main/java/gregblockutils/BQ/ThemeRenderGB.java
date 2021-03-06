package gregblockutils.BQ;

import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.themes.IThemeRenderer;
import betterquesting.api.enums.EnumQuestState;
import betterquesting.api.properties.NativeProps;
import betterquesting.api.questing.IQuest;
import betterquesting.api.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.UUID;

public class ThemeRenderGB extends GuiElement implements IThemeRenderer {
    private int[] iconColors = new int[]{new Color(0.4F, 0.45F, 0.777F).getRGB(), new Color(0F, 0.165F, 0.815F).getRGB(), new Color(1F, 0.965F, 0.647F).getRGB(), new Color(1F, 0.901F, 0F).getRGB()};

    public void setLineColors(int locked, int incomplete, int complete) {
        iconColors[0] = locked;
        iconColors[1] = incomplete;
        iconColors[3] = complete;
    }

    public void setIconColors(int locked, int incomplete, int pending, int complete) {
        iconColors[0] = locked;
        iconColors[1] = incomplete;
        iconColors[2] = pending;
        iconColors[3] = complete;
    }

    @Override
    public void drawLine(IQuest quest, UUID playerID, float x1, float y1, float x2, float y2, int mx, int my, float partialTick) {
        boolean isMain = quest == null ? false : quest.getProperties().getProperty(NativeProps.MAIN);
        EnumQuestState qState = quest != null && playerID != null ? quest.getState(playerID) : EnumQuestState.LOCKED;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        int cl = this.getQuestLineColor(qState);
        float lr = (float) (cl >> 16 & 255) / 255.0F;
        float lg = (float) (cl >> 8 & 255) / 255.0F;
        float lb = (float) (cl & 255) / 255.0F;
        GlStateManager.color(lr, lg, lb, 1F);
        GL11.glLineWidth(isMain ? 8.0F : 4.0F);
        GL11.glEnable(2852);
        GL11.glLineStipple(8, (short) -1);
        GL11.glBegin(1);
        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);
        GL11.glEnd();
        GL11.glLineStipple(1, (short) 32767);
        GL11.glDisable(2852);
        GlStateManager.enableTexture2D();
        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.popMatrix();
    }

    @Override
    public void drawIcon(IQuest quest, UUID playerID, float px, float py, float sx, float sy, int mx, int my, float partialTick) {
        boolean isMain = quest == null ? false : quest.getProperties().getProperty(NativeProps.MAIN);
        EnumQuestState qState = quest == null || playerID == null ? EnumQuestState.LOCKED : quest.getState(playerID);
        boolean hover = mx >= px && my >= py && mx < px + sx && my < py + sy;

        GlStateManager.pushMatrix();

        int ci = getQuestIconColor(qState, qState == EnumQuestState.LOCKED ? 0 : (!hover ? 1 : 2));
        float ir = (float) (ci >> 16 & 255) / 255.0F;
        float ig = (float) (ci >> 8 & 255) / 255.0F;
        float ib = (float) (ci & 255) / 255.0F;
        GlStateManager.color(ir, ig, ib, 1F);

        GlStateManager.translate(px, py, 0F);
        float sw = sx / 24;
        float sh = sy / 24;
        GlStateManager.scale(sw, sh, 1F);

        Minecraft.getMinecraft().renderEngine.bindTexture(currentTheme().getGuiTexture());
        this.drawTexturedModalRect(0, 0, (isMain ? 24 : 0), 104, 24, 24);

        if (quest == null) {
            RenderUtils.RenderItemStack(Minecraft.getMinecraft(), new ItemStack(Items.NETHER_STAR), 4, 4, "");
        } else if (quest.getItemIcon() != null) {
            RenderUtils.RenderItemStack(Minecraft.getMinecraft(), quest.getItemIcon().getBaseStack(), 4, 4, "");
        }

        GlStateManager.popMatrix();
    }

    private int getQuestLineColor(EnumQuestState state) {
        Color c = new Color(this.iconColors[state.ordinal()]);
        return state == EnumQuestState.UNLOCKED && Minecraft.getSystemTime() / 1000L % 2L == 0L ? (new Color((float) c.getRed() / 255.0F * 0.5F, (float) c.getGreen() / 255.0F * 0.5F, (float) c.getBlue() / 255.0F * 0.5F)).getRGB() : c.getRGB();
    }

    private int getQuestIconColor(EnumQuestState state, int hoverState) {
        Color c = new Color(this.iconColors[state.ordinal()]);
        return hoverState == 1 ? (new Color((float) c.getRed() / 255.0F * 0.75F, (float) c.getGreen() / 255.0F * 0.75F, (float) c.getBlue() / 255.0F * 0.75F)).getRGB() : c.getRGB();
    }

    @Override
    public void drawThemedPanel(int x, int y, int w, int h) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(currentTheme().getGuiTexture());
        int w2 = w - w % 16;
        int h2 = h - h % 16;

        for (int i = 0; i < w2; i += 16) {
            for (int j = 0; j < h2; j += 16) {
                int tx = 16;
                int ty = 16;
                if (i == 0) {
                    tx -= 16;
                } else if (i == w2 - 16) {
                    tx += 16;
                }

                if (j == 0) {
                    ty -= 16;
                } else if (j == h2 - 16) {
                    ty += 16;
                }

                this.drawTexturedModalRect(x + i, y + j, tx, ty, 16, 16);
            }
        }
    }
}
