package Discarpet.script.functions;

import Discarpet.script.values.ServerValue;
import Discarpet.script.values.UserValue;

import carpet.script.Expression;
import carpet.script.value.*;

import static Discarpet.Discarpet.scarpetException;

public class Get {
    public static void apply(Expression expr) {
        expr.addLazyFunction("dc_get_display_name", 2, (c, t, lv) -> {
            Value user = lv.get(0).evalValue(c);
            Value server = lv.get(1).evalValue(c);
            if(!(user instanceof UserValue)) scarpetException("dc_get_display_name","user",0);
            if(!(server instanceof ServerValue)) scarpetException("dc_get_display_name","server",1);
            return (cc, tt) -> StringValue.of(((ServerValue) server).server.getDisplayName(((UserValue) user).user));
        });
    }
}
