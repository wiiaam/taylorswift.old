# Commands for Personal-Bot

**Note:** All commands listed start with the bot's command char, unless one is specifed. 
The current command char can be found with .help

## Public Commands

### Help

`.help`, `help` - Displays help.

`.source`, `source` - Displays a link to the current repository.

`.license`, `license` - Displays license information.

`.bots` - Responds to [IBIP](https://github.com/Teknikode/IBIP).

### Administration

(Note: this requires administration commands to be turned on in a channel)

`kb <user>` - Kicks a user and bans their host.

`topic <topic>` - Changes the topic.

### Web Tools
 
`g <search>`, `google <search>` - Searches google for the search term and returns the first result.

`yt <search>`, `youtube <search>` - Searches YouTube for the search term and returns the first video found. 

`ud <search>` - Searches urbandictionary for a term and returns the result.

`w <place> weather <place>` - Displays weather information for a specified place.

`steaminfo <user>` - Displays steam information on the specified user.

`cb <message>` - Send a message to cleverbot.

`shorten <url>` - Shorten a url

### Voting

`vote <topic>` - Starts a vote. 

`voteyes` - Vote yes.

`voteno` - Vote no.

### CTCP

`ver <user>`, `version <user>` - Sends a ctcp VERSION to the user and displays the result if a response is received.

`time <user>` - Sends a ctcp TIME to the user and displays the result if a response is received.

`ping <user>` - Sends a ctcp PING to the user and displays the result if a response is received.

### Github user info

`gh <user>, github <user>` - Displays github profile information associated with the user.

`repos <user>` - Displays all repos by the user.

`repo <user> <repo>` - Displays information of a github repository.

### Money

`bene` - Get a payment.

`pokies <amount>` - Bet a certain amount.

`mug <user>` - Steal money from a user.

`durry` - Buy a durry.

`give <user> <amount>` - Give money to a user.

### Random

`kill <bot> <channel>` - Kill a bot that is exploitable via `INVITE`.

`> <message>` - Displays a >greentext of the message.

`imply` - Displays >implying message

`8ball` - Ask the magic 8ball a question.

`fortune` - Displays your fortune.

`lyrics` - Displays a random Taylor Swift lyric.

`post n00dz` - Posts n00dz. **NSFW**.

`london <message>` - :^) limit 10 chars. Usually kept turned off to stop channel spamming.

`cowsay <message>` - Makes the cow say.

`quote <user>` - Displays a random quote from the user.

`listtriggers` - Lists all the current triggers.

`stopspamming` - Stops `spam` messages.

## Admin Commands

### Bot administration

`modules <load/unload/reload> <module>` - Modify the loaded modules.

`admin <add/del> <user>` - Add/delete an admin.

`char <command char>` - Changes the command char to the one specified. If `self` is specified, the char will be `<nickname>: `

`nick <nick>` - Changes the bots nickname.

`join <channels>` - Joins the specified channels.

`leave` - Leaves the channel where the command was said.

`raw <message>` - Sends a raw IRC format message.

`say <message>` - Says the message in the channel where the command was said.

`pm <target> <message>` - Sends a PRIVMSG to the target.

`notice <target> <message>` - Sends a NOTICE to the target.

`ignore <user>` - Ignores the user.

`unignore <user>` - Unignores the user.

`quit` - Close connection and end the process.

### URL Title Reporting

`titleson <room/user>` - Turns on title reporting for the user/room

`titlesoff <room/user>` - Turns off title reporting for the user/room

### Triggers

`trigger <trigger> : <message>` - Adds the trigger.

`untrigger <trigger>` - Removes the trigger.

`triggerson` - Turns triggers on.

`triggersoff` - Turns triggers off.

### Toggles

`lyricson <room>` - Turns on random Taylor Swift lyric posting.

`lyricsoff <room>` - Turns off random Taylor Swift lyric posting.

`kiwion` - Messages any KiwiIRC users that join.

`kiwioff` - Turns off messages.

`londonon` - Turns london on.

`londonoff` - Turns london off.

`cowsayon` - Turn cowsay on.

`cowsayoff` - Turn cowsay off.

### Spam commands

`spam <ircmessage>` - Sends an ircformat message every 2 seconds.

`sadfrog` - feels bad man.

### Bro Detection

`addbro <user>` - Adds a bro.

`delbro <user>` - Deletes a bro.
