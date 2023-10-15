package cc.sakurarain.globalmonitor;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class GlobalMonitor extends Plugin implements Listener {
    private final String Channel = "GlobalMonitor".toLowerCase();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getProxy().getPluginManager().registerListener(this, this);
        this.getProxy().registerChannel(this.Channel);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getProxy().unregisterChannel(this.Channel);
    }

    @EventHandler
    public void onChatEvent(ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            String server = player.getServer().getInfo().getName();
            String name = player.getName();
            String ip = event.getSender().getSocketAddress().toString().replace("/", "");
            String text = ChatColor.BLUE + "[" + server + "|" + ip + "|" + name + "]" + ChatColor.GREEN + " >>> " + (event.getMessage().startsWith("/") ? ChatColor.YELLOW : ChatColor.WHITE) + event.getMessage();
            this.getLogger().info(text);
        }
    }

    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent event) {
        if (event.getTag().equals(this.Channel) && event.getSender() instanceof Server) {
            String server = ((Server) event.getSender()).getInfo().getName();
            String text = ChatColor.LIGHT_PURPLE + "[" + server + "]" + ChatColor.GREEN + " >>> " + ChatColor.RED + new String(event.getData());
            this.getLogger().info(text);
        }
    }
}
