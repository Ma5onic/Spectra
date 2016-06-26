/*
 * Copyright 2016 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spectra.commands;

import java.util.List;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.PermissionUtil;
import spectra.Argument;
import spectra.Command;
import spectra.PermLevel;
import spectra.Sender;
import spectra.SpConst;
import spectra.datasources.Feeds;

/**
 *
 * @author John Grosh (jagrosh)
 */
public class Feed extends Command {
    
    public Feed()
    {
        this.command = "feed";
        this.help = "sets or removes a feed; `"+SpConst.PREFIX+"feed help` for more";
        this.level = PermLevel.ADMIN;
        this.arguments = new Argument[]{
            new Argument("feedtype",Argument.Type.SHORTSTRING,false)
        };
        this.children = new Command[]{
            new FeedAnnouncements(),
            new FeedList(),
            new FeedModlog(),
            new FeedRemove(),
            new FeedServerlog(),
            new FeedTaglog()
        };
        this.availableInDM = false;
    }
    @Override
    protected boolean execute(Object[] args, MessageReceivedEvent event) {
        String invalidtype = args[0]==null?null:(String)(args[0]);
        String str;
        if(invalidtype==null)
            str = "The `feed` command assigns "+SpConst.BOTNAME+" to send certain type of messages to a channel";
        else
            str = SpConst.ERROR+"That is not a valid feed type!";
        Sender.sendResponse(str + "\nPlease use `"+SpConst.PREFIX+"feed help` for a valid list of feeds", event.getChannel(), event.getMessage().getId());
        return true;
    }
    
    
    private class FeedModlog extends Command {
        public FeedModlog()
        {
            this.command = "modlog";
            this.help = "sets the `modlog` feed, which displays moderation actions taken on the server";
            this.level = PermLevel.ADMIN;
            this.arguments = new Argument[]{
                new Argument("channel",Argument.Type.TEXTCHANNEL,false)
            };
            this.availableInDM = false;
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            TextChannel tchan = (TextChannel)(args[0]);
            if(tchan==null)
                tchan = event.getTextChannel();
            //check bot permissions for channel
            if(!PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_WRITE, tchan) || !PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_READ, tchan))
            {
                Sender.sendResponse(String.format(SpConst.NEED_PERMISSION,Permission.MESSAGE_READ+", "+Permission.MESSAGE_WRITE+
                        ", and preferably "+Permission.MESSAGE_ATTACH_FILES), event.getChannel(), event.getMessage().getId());
                return false;
            }
            String str = "";
            String[] current = Feeds.getInstance().feedForGuild(event.getGuild(), Feeds.Type.MODLOG);
            if(current!=null)
                str+=SpConst.WARNING+"Feed "+Feeds.Type.MODLOG+" has been removed from <#"+current[Feeds.CHANNELID]+">\n";
            Feeds.getInstance().set(new String[]{tchan.getId(),Feeds.Type.MODLOG.toString(),event.getGuild().getId(),""});
            str+=SpConst.SUCCESS+"Feed "+Feeds.Type.MODLOG+" has been added to <#"+tchan.getId()+">";
            Sender.sendResponse(str, event.getChannel(), event.getMessage().getId());
            return true;
        }
    }
    
    private class FeedServerlog extends Command {
        public FeedServerlog()
        {
            this.command = "serverlog";
            this.help = "sets the `serverlog` feed, which displays various activity in the server";
            this.level = PermLevel.ADMIN;
            this.arguments = new Argument[]{
                new Argument("channel",Argument.Type.TEXTCHANNEL,false)
            };
            this.availableInDM = false;
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            TextChannel tchan = (TextChannel)(args[0]);
            if(tchan==null)
                tchan = event.getTextChannel();
            //check bot permissions for channel
            if(!PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_WRITE, tchan) || !PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_READ, tchan))
            {
                Sender.sendResponse(String.format(SpConst.NEED_PERMISSION,Permission.MESSAGE_READ+", "+Permission.MESSAGE_WRITE+
                        ", and preferably "+Permission.MESSAGE_ATTACH_FILES), event.getChannel(), event.getMessage().getId());
                return false;
            }
            String str = "";
            String[] current = Feeds.getInstance().feedForGuild(event.getGuild(), Feeds.Type.SERVERLOG);
            if(current!=null)
                str+=SpConst.WARNING+"Feed "+Feeds.Type.SERVERLOG+" has been removed from <#"+current[Feeds.CHANNELID]+">\n";
            Feeds.getInstance().set(new String[]{tchan.getId(),Feeds.Type.SERVERLOG.toString(),event.getGuild().getId(),""});
            str+=SpConst.SUCCESS+"Feed "+Feeds.Type.SERVERLOG+" has been added to <#"+tchan.getId()+">";
            Sender.sendResponse(str, event.getChannel(), event.getMessage().getId());
            return true;
        }
    }
    
    private class FeedTaglog extends Command {
        public FeedTaglog()
        {
            this.command = "taglog";
            this.help = "sets the `taglog` feed, which displays changes to tags by members of the server";
            this.level = PermLevel.ADMIN;
            this.arguments = new Argument[]{
                new Argument("channel",Argument.Type.TEXTCHANNEL,false)
            };
            this.availableInDM = false;
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            TextChannel tchan = (TextChannel)(args[0]);
            if(tchan==null)
                tchan = event.getTextChannel();
            //check bot permissions for channel
            if(!PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_WRITE, tchan) || !PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_READ, tchan))
            {
                Sender.sendResponse(String.format(SpConst.NEED_PERMISSION,Permission.MESSAGE_READ+", "+Permission.MESSAGE_WRITE+
                        ", and preferably "+Permission.MESSAGE_ATTACH_FILES), event.getChannel(), event.getMessage().getId());
                return false;
            }
            String str = "";
            String[] current = Feeds.getInstance().feedForGuild(event.getGuild(), Feeds.Type.TAGLOG);
            if(current!=null)
                str+=SpConst.WARNING+"Feed "+Feeds.Type.TAGLOG+" has been removed from <#"+current[Feeds.CHANNELID]+">\n";
            Feeds.getInstance().set(new String[]{tchan.getId(),Feeds.Type.TAGLOG.toString(),event.getGuild().getId(),""});
            str+=SpConst.SUCCESS+"Feed "+Feeds.Type.TAGLOG+" has been added to <#"+tchan.getId()+">";
            Sender.sendResponse(str, event.getChannel(), event.getMessage().getId());
            return true;
        }
    }
    
    private class FeedAnnouncements extends Command {
        public FeedAnnouncements()
        {
            this.command = "announcements";
            this.help = "sets the `announcements` feed, which relays important updates and information about "+SpConst.BOTNAME;
            this.level = PermLevel.ADMIN;
            this.arguments = new Argument[]{
                new Argument("channel",Argument.Type.TEXTCHANNEL,false)
            };
            this.availableInDM = false;
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            TextChannel tchan = (TextChannel)(args[0]);
            if(tchan==null)
                tchan = event.getTextChannel();
            //check bot permissions for channel
            if(!PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_WRITE, tchan) || !PermissionUtil.checkPermission(event.getJDA().getSelfInfo(), Permission.MESSAGE_READ, tchan))
            {
                Sender.sendResponse(String.format(SpConst.NEED_PERMISSION,Permission.MESSAGE_READ+", "+Permission.MESSAGE_WRITE+
                        ", and preferably "+Permission.MESSAGE_ATTACH_FILES), event.getChannel(), event.getMessage().getId());
                return false;
            }
            String str = "";
            String[] current = Feeds.getInstance().feedForGuild(event.getGuild(), Feeds.Type.ANNOUNCEMENTS);
            if(current!=null)
                str+=SpConst.WARNING+"Feed "+Feeds.Type.ANNOUNCEMENTS+" has been removed from <#"+current[Feeds.CHANNELID]+">\n";
            Feeds.getInstance().set(new String[]{event.getTextChannel().getId(),Feeds.Type.ANNOUNCEMENTS.toString(),event.getGuild().getId(),""});
            str+=SpConst.SUCCESS+"Feed "+Feeds.Type.ANNOUNCEMENTS+" has been added to <#"+event.getTextChannel().getId()+">";
            Sender.sendResponse(str, event.getChannel(), event.getMessage().getId());
            return true;
        }
    }
    
    private class FeedList extends Command {
        public FeedList()
        {
            this.command = "list";
            this.help = "shows the current feeds on the server";
            this.level = PermLevel.ADMIN;
            this.availableInDM = false;
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            List<String[]> feeds = Feeds.getInstance().findFeedsForGuild(event.getGuild());
            if(feeds.isEmpty())
            {
                Sender.sendResponse(SpConst.WARNING+"No feeds found on **"+event.getGuild().getName()+"**", event.getChannel(), event.getMessage().getId());
                return true;
            }
            StringBuilder builder = new StringBuilder(SpConst.SUCCESS).append(feeds.size()).append(" feeds found on **").append(event.getGuild().getName()).append("**:");
            feeds.stream().forEach((feed) -> {
                builder.append("\n`").append(feed[Feeds.FEEDTYPE]).append("` - <#").append(feed[Feeds.CHANNELID])
                        .append(">").append((feed[Feeds.DETAILS]!=null && !feed[Feeds.DETAILS].equals("")) ? " : "+feed[Feeds.DETAILS] : "");
            });
            Sender.sendResponse(builder.toString(), event.getChannel(), event.getMessage().getId());
            return true;
        }
    }
    
    private class FeedRemove extends Command {
        public FeedRemove()
        {
            this.command = "remove";
            this.help = "removes a feed";
            this.level = PermLevel.ADMIN;
            this.availableInDM = false;
            this.arguments = new Argument[]{
                new Argument("feed name",Argument.Type.LONGSTRING,true)
            };
        }
        @Override
        protected boolean execute(Object[] args, MessageReceivedEvent event) {
            String feedname = (String)(args[0]);
            List<String[]> feeds = Feeds.getInstance().findFeedsForGuild(event.getGuild());
            if(feeds.isEmpty())
            {
                Sender.sendResponse(SpConst.WARNING+"No feeds found on **"+event.getGuild().getName()+"**", event.getChannel(), event.getMessage().getId());
                return true;
            }
            for(String[] feed : feeds)
                if((feed[Feeds.FEEDTYPE]+((feed[Feeds.DETAILS]!=null && !feed[Feeds.DETAILS].equals("")) ? " "+feed[Feeds.DETAILS] : "")).equalsIgnoreCase(feedname))
                {
                    Feeds.getInstance().removeFeed(feed);
                    Sender.sendResponse(SpConst.SUCCESS+"Removed feed `"+feed[Feeds.FEEDTYPE]+"` from <#"+feed[Feeds.CHANNELID]+">", event.getChannel(), event.getMessage().getId());
                    return true;
                }
            Sender.sendResponse(SpConst.ERROR+"No feeds found matching \""+feedname+"\"", event.getChannel(), event.getMessage().getId());
            return false;
        }
    }
}
