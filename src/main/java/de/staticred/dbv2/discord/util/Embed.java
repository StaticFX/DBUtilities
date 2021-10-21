package de.staticred.dbv2.discord.util;

import de.staticred.dbv2.DBUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Small class to help create embeds in 1 line
 *
 * @author Devin
 * @version 1.0.0
 */
public class Embed {

    private EmbedBuilder builder;

    public Embed(String context, String header, boolean useAuthorTag, Color color, String pictureURL) {

        this.builder = new EmbedBuilder();

        builder.setDescription(context);
        builder.setTitle(header);
        builder.setColor(color);
        builder.setThumbnail(pictureURL);

        if (useAuthorTag) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DBUtil.TIME_PATTERN);
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            builder.setFooter(time + " sent by " + DBUtil.getINSTANCE().getMcMessagesFileHandler().getFooter());
        }
    }

    public Embed(String context, String header, boolean useAuthorTag, Color color) {
        this.builder = new EmbedBuilder();

        builder.setDescription(context);
        builder.setTitle(header);
        builder.setColor(color);

        if (useAuthorTag) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DBUtil.TIME_PATTERN);
            String time = dateFormat.format(new Date(System.currentTimeMillis()));
            builder.setFooter(time + " send by " + DBUtil.getINSTANCE().getMcMessagesFileHandler().getFooter());
        }
    }

    public MessageEmbed build() {
        return builder.build();
    }
}
