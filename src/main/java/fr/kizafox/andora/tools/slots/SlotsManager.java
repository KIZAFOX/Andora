package fr.kizafox.andora.tools.slots;

import fr.kizafox.andora.Andora;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;

public class SlotsManager {

    protected final Andora instance;
    private Field maxPlayersField;

    public SlotsManager(Andora instance) {
        this.instance = instance;
    }

    public void changeSlots(int slots) throws ReflectiveOperationException {
        Method serverGetHandle = this.instance.getServer().getClass().getDeclaredMethod("getHandle");
        Object playerList = serverGetHandle.invoke(this.instance.getServer());

        if (this.maxPlayersField == null) {
            this.maxPlayersField = getMaxPlayersField(playerList);
        }

        this.maxPlayersField.setInt(playerList, slots);
    }

    public void updateServerProperties() {
        Properties properties = new Properties();
        File propertiesFile = new File("server.properties");

        try {
            try (InputStream is = Files.newInputStream(propertiesFile.toPath())) {
                properties.load(is);
            }

            String maxPlayers = Integer.toString(this.instance.getServer().getMaxPlayers());

            if (properties.getProperty("max-players").equals(maxPlayers)) {
                return;
            }

            this.instance.getLogger().info("Saving max players to server.properties...");
            properties.setProperty("max-players", maxPlayers);

            try (OutputStream os = Files.newOutputStream(propertiesFile.toPath())) {
                properties.store(os, "Minecraft server properties");
            }
        } catch (IOException e) {
            this.instance.getLogger().log(Level.SEVERE, "An error occurred while updating the server properties", e);
        }
    }

    private Field getMaxPlayersField(Object playerList) throws ReflectiveOperationException {
        Class<?> playerListClass = playerList.getClass().getSuperclass();

        try {
            Field field = playerListClass.getDeclaredField("maxPlayers");
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            for (Field field : playerListClass.getDeclaredFields()) {
                if (field.getType() != int.class) {
                    continue;
                }

                field.setAccessible(true);

                if (field.getInt(playerList) == Bukkit.getMaxPlayers()) {
                    return field;
                }
            }

            throw new NoSuchFieldException("Unable to find maxPlayers field in " + playerListClass.getName());
        }
    }

}