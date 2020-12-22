package AvocadoBot.commands.fun;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.JSONException;
import org.json.JSONObject;

public class XKCDCommand implements MessageCreateListener {

    private static String prefix;

    public XKCDCommand(String pre) {
        prefix = pre;
    }

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
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    private static BufferedImage readImageFromUrl(String str) throws MalformedURLException, IOException {
        URL imageUrl = new URL(str);
        try (InputStream in = imageUrl.openStream()) {
            BufferedImage image = ImageIO.read(in);
            return image;
        }
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "xkcd")) {
            try {
                JSONObject j1 = readJsonFromUrl("https://xkcd.com/info.0.json");
                int num1 = j1.getInt("num");
                int num2 = (int) Math.round(Math.random() * num1) + 1;
                String link = "https://xkcd.com/" + Integer.toString(num2) + "/info.0.json";
                j1 = readJsonFromUrl(link);
                String imglink = j1.getString("img");
                String alt = j1.getString("alt");
                String title = j1.getString("safe_title");
                BufferedImage b1 = readImageFromUrl(imglink);
                EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("XKCD Comic #" + num2 + ": "  + title)
                    .setDescription(alt)
                    .setImage(b1)
                    .setFooter("Read more comics at https://xkcd.com/")
                    .setColor(new Color(204, 44, 44));
                event.getChannel().sendMessage(embed);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(XKCDCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
