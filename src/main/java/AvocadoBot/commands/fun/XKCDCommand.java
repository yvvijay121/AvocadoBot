package AvocadoBot.commands.fun;

import de.btobastian.sdcf4j.Command;
import de.btobastian.sdcf4j.CommandExecutor;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XKCDCommand implements CommandExecutor {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private static BufferedImage readImageFromUrl(String str) throws IOException {
        URL imageUrl = new URL(str);
        try (InputStream in = imageUrl.openStream()) {
            return ImageIO.read(in);
        }
    }

    @Command(aliases = {"xkcd", "x"}, description = "Gets a random comic from XKCD.", usage = "xkcd")
    public void onMessageCreate(TextChannel channel) {
        try {
            JSONObject j1 = readJsonFromUrl("https://xkcd.com/info.0.json");
            int num1 = j1.getInt("num");
            int num2 = (int) Math.round(Math.random() * num1) + 1;
            String link = "https://xkcd.com/" + num2 + "/info.0.json";
            j1 = readJsonFromUrl(link);
            String imglink = j1.getString("img");
            String alt = j1.getString("alt");
            String title = j1.getString("safe_title");
            BufferedImage b1 = readImageFromUrl(imglink);
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("XKCD Comic #" + num2 + ": " + title)
                    .setDescription(alt)
                    .setImage(b1)
                    .setFooter("Read more comics at https://xkcd.com/")
                    .setColor(new Color(204, 44, 44));
            channel.sendMessage(embed);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(XKCDCommand.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
