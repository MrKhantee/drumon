package se.tube42.drum.view;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.Input.*;

import se.tube42.lib.tweeny.*;
import se.tube42.lib.ks.*;
import se.tube42.lib.scene.*;
import se.tube42.lib.util.*;
import se.tube42.lib.item.*;

import se.tube42.drum.data.*;
import se.tube42.drum.logic.*;

import static se.tube42.drum.data.Constants.*;


public class PressItem
extends BaseButton
implements TweenListener
{
    private static final int
          MSG_UPDATE = 0
          ;

    private int tile, icon;
    private int new_tile, new_icon, new_color;

    public PressItem(int tile, int icon, int color)
    {
        setTile(tile);
        setIcon(icon);
        setColor(color);
    }

    //

    public void setActive(boolean v)
    {
        setAlpha(0.3f, v ? 1 : ALPHA_INACTIVE);
    }


    public void mark0()
    {
        final float r = ServiceProvider.getRandom(0.1f, 0.2f);

        set(ITEM_S, 1.05f).configure(r, null)
              .tail(1.00f).configure(r, null);
    }

    //
    public void setTile(int tile)
    {
        this.tile = tile;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public void change(int color, int icon,
              boolean active, boolean animate)
    {
        new_tile = active ? TILE_BUTTON1 : TILE_BUTTON0;


        if(!animate) {
            setColor(color);
            setIcon(icon);
            setTile(new_tile);
       } else {
           new_color = color;
           new_icon = icon;

           final float r = ServiceProvider.getRandom(0.2f, 0.25f);

           set(ITEM_S, 1, 0.1f).configure(r, null).finish(this,MSG_UPDATE)
                 .tail(1).configure(r, null);

           set(ITEM_A, 1, 0).configure(r / 2, null)
                 .pause(r)
                 .tail(1).configure(r / 2, null);

           set(ITEM_R, 0, +30).configure(r, null)
                 .tail(0).configure(r, null);

           set(ITEM_V, 0, -w * 2).configure(r, TweenEquation.QUAD_OUT)
                 .tail(0).configure(r, null);

        }
    }

    //

    public void draw(SpriteBatch sb)
    {
        final float a = getAlpha();
        final float s = getScale();
        final float x = getX() + get(ITEM_V);
        final float y = getY();
        final float r = getRotation();
        final float w2 = w / 2;
        final float h2 = h / 2;
        final float hp = World.halfpixel;

        // draw tile
        if(tile != -1) {
            sb.setColor(cr, cg, cb, a);
            sb.draw(World.tex_tiles[tile],
                    x + hp, y + hp,
                    w2, h2,
                    w, h,
                    s, s, r);
        }

        // draw icon
        if(icon != -1) {
            final TextureRegion tr = World.tex_icons[icon];
            final float w4 = w / 4;
            final float h4 = h / 4;

            sb.setColor(1, 1, 1, a);
            sb.draw(tr,
                    w4 + x + hp, h4 + y + hp,
                    w4, h4,
                    w2, h2,
                    s, s, r);

        }
    }

    public void onFinish(Item item, int index, int msg)
    {
        switch(msg) {
        case MSG_UPDATE:
            setColor(new_color);
            setIcon(new_icon);
            setTile(new_tile);
            break;
        }
    }
}
