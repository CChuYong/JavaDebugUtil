package kr.chuyong.jdu;

import api.cosmoage.bukkit.commands.$BukkitCommandHandler;
import kr.chuyong.jdu.commands.*;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.plugin.java.JavaPlugin;

public class JavaDebugUtil extends JavaPlugin {
    public static SessionFactory sessionFactory;
    public void onEnable(){
        sessionFactory = new SessionFactory();
        try {
            $BukkitCommandHandler.registerCommands(this, new CommandField());
            $BukkitCommandHandler.registerCommands(this, new CommandClass());
            $BukkitCommandHandler.registerCommands(this, new CommandInstance());
            $BukkitCommandHandler.registerCommands(this, new CommandMethod());
            $BukkitCommandHandler.registerCommands(this, new CommandCinstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
