package AvocadoBot.commands.fun;

import AvocadoBot.Main;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JokeCommand implements MessageCreateListener {

    private static String prefix;

    public JokeCommand(String pre) {
        prefix = pre;
    }

    private static String readAll(File f) throws IOException {
        Scanner sc = new Scanner(f);
        //StringBuffer to store the contents
        StringBuilder sb = new StringBuilder();
        //Appending each line to the buffer
        while (sc.hasNext()) {
            sb.append(" ").append(sc.nextLine());
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(URLConnection url) throws IOException, JSONException {
        JSONObject json = null;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(url.getInputStream()));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            json = new JSONObject(jsonText);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (event.getMessageContent().equalsIgnoreCase(prefix + "joke")) {
            JSONObject j1 = new JSONObject();
            try {
                URL url = new URL("https://www.reddit.com/r/jokes.json?limit=100?sort=hot");
                URLConnection hc = url.openConnection();
                hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.101 Mobile Safari/537.36");
                j1 = readJsonFromUrl(hc);
                File f1 = new File("C:\\Programming_Projects\\ResourcesForMigratableProjects\\jokes.json");
                try (FileWriter fw1 = new FileWriter(f1)) {
                    fw1.write(j1.toString(3));
                }
            } catch (IOException | JSONException ex) {
                Logger.getLogger(JokeCommand.class.getName()).log(Level.WARNING, null, ex);
            }
            if (j1.isEmpty()) {
                try {
                    File f2 = new File("C:\\Programming_Projects\\ResourcesForMigratableProjects\\jokes.json");
                    j1 = new JSONObject(readAll(f2));
                    System.out.println("everything went fine (old read)");
                } catch (IOException ex) {
                    Logger.getLogger(JokeCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!j1.isEmpty()) {
                JSONObject j2 = j1.getJSONObject("data");
                JSONArray ja1 = j2.getJSONArray("children");
                int randomInt = (int) Math.round(Math.random() * ja1.length());
                JSONObject i1 = ja1.getJSONObject(randomInt);
                if (!i1.getJSONObject("data").get("link_flair_text").toString().equals("MODPOST")) {
                    String title = i1.getJSONObject("data").getString("title");
                    String joke = i1.getJSONObject("data").getString("selftext");
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(title)
                            .setDescription(joke)
                            .setFooter("This joke was scraped from r/jokes.")
                            .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                } else {
                    randomInt = (int) Math.round(Math.random() * ja1.length()) + 1;
                    JSONObject iw1 = ja1.getJSONObject(randomInt);
                    while (!iw1.getJSONObject("data").getString("link_flair_text").equals("MODPOST")) {
                        iw1 = ja1.getJSONObject((int) Math.round(Math.random() * ja1.length()) + 1);
                    }
                    String title = iw1.getJSONObject("data").getString("title");
                    String joke = iw1.getJSONObject("data").getString("title");
                    EmbedBuilder embed = new EmbedBuilder()
                            .setTitle(title)
                            .setDescription(joke)
                            .setFooter("This joke was scraped from r/jokes.")
                            .setColor(new Color(204, 44, 44));
                    event.getChannel().sendMessage(embed);
                }
            } else {
                event.getChannel().sendMessage("something went wrong.");
            }
        }
    }
}
