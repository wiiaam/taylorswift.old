# Commands for Personal-Bot

**Note:** All commands listed start with the bot's command char, unless one is specifed. 
The current command char can be found with .help

## Public Commands

`.help`, `help` - Displays help.

`.source`, `source` - Displays a link to the current repository.

`.license`, `license` - Displays license information.

`.bots` - Responds to [IBIP](https://github.com/Teknikode/IBIP).

`g <search>`, `google <search>` - Searches google for the search term and returns the first result.

`yt <search>`, `youtube <search>` - Searches YouTube for the search term and returns the first video found. 

`ud <search>` - Searches urbandictionary for a term and returns the result.

`steaminfo <user>` - Displays steam information on the specified user.

`8ball` - Ask the magic 8ball a question.

`fortune` - Displays your fortune.

`lyrics` - Displays a random Taylor Swift lyric.

`vote <topic>` - Starts a vote. Votes can be placed with `voteyes` and `voteno`.

`ver <user>`, `version <user>` - Sends a ctcp VERSION to the user and displays the result if a response is received 

`time <user>` - Sends a ctcp TIME to the user and displays the result if a response is received.

`ping <user>` - Sends a ctcp PING to the user and displays the result if a response is received.

`post n00dz` - Posts n00dz. **NSFW**.

`gt <message>` - Displays a >greentext of the message.

`>` - Displays >implying message

`kek <kek>` - Displays the kek associated to the param.

`listtriggers` - Lists all the triggers.

`london <message>` - :^) limit 10 chars. Usually kept turned off to stop channel spamming.

`quote <user>` - Displays a random quote from the user.

### Money

`bene` - Get a payment.

`pokies <amount>` - Bet a certain amount.

`mug <user>` - Steal money from a user.

`durry` - Buy a durry.

## Admin commands

`char <command char>` - Changes the command char to the one specified. If `self` is specified, the char will be 
`<nickname>:`

`nick <nick>` - Changes the bots nickname.

`join <channels>` - Joins the specified channels.

`leave` - Leaves the channel where the command was said.

`raw <message>` - Sends a raw IRC format message.

`say <message>` - Says the message in the channel where the command was said.

`pm <target> <message>` Sends a PRIVMSG to the target.

`notice <target> <message>` Sends a NOTICE to the target.

`lyricson` - Turns on random Taylor Swift lyric posting.

`lyricsoff` - Turns off random Taylor Swift lyric posting.

`kiwion` - Messages any KiwiIRC users that join.

`kiwioff` - Turns off messages.

`trigger <trigger> : <message>` - Adds the trigger.

`untrigger <trigger>` - Removes the trigger.

`triggers <on/off>` - Turns triggers on/off.

`londonon` - Turns london on.

`londonoff` - Turns london off.

`ignore <user>` - Ignores the user.

`unignore <user>` - Unignores the user.
