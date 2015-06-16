package extras;

public class Lyrics {
	
	private static String[] shortlyrics = {"I don't know about you..", "Never ever ever", "And thats the way I loved you",
			"Yeah they'll tell you now you're the lucky one", "Oh my my my", "In my best dress, fearless",
			"I'm only me when i'm with you", "I had the best day with you today", "I've got a blank space baby",
			"We never go out of style", "It's a love story baby just say yes", "You should've said no",
			"I go back to december all the time", "These things will change", 
			"You are the best thing, that's ever been mine", "You, with your words like knives","Absent mindedly makin’ me want you"
	};
	
	private static String[] lyrics = {"I said leave, but all i really want is you", 
			"Wish you could go back and tell yourself what you know now",
			"I’m no one special, just another wide eyed girl who’s desperately in love with you",
			"I realize you love yourself more than you could ever love me","And when you take, you take the very best of me",
			"You could write a book on how to ruin someone's perfect day","My thoughts will echo your name until I see you again",
			"If we loved again, I swear I'd love you right","He's the song in the car I keep singing. Don't know why I do",
			"I take a step back, and let you go. I told you I'm not bulletproof; Now you know",
			"Long handwritten note deep in your pocket. Words, how little they mean when you're a little too late",
			"It's alright, just wait and see, your string of lights is still bright to me",
			"I know looks can be deceiving but I know saw a light in you",
			"And all I think about is how to make you think of me, and everything that we could be",
			"I'm only up when you're not down. Don't wanna fly if you're still on the ground",
			"This is a new year. A new beginning. And things will change", "You gave me roses and I left them there to die",
			"These days I haven't been sleeping. Staying up playing back myself leavin",
			"It turns out freedom ain't nothing but missing you, Wishing I'd realized what I had when you were mine",
			"I'd go back to December, turn around and change my own mind", 
			"I miss your tanned skin, your sweet smile, so good to me, so right.",
			"And how you held me in your arms that September night. The first time you ever saw me cry",
			"Maybe this is wishful thinking, Probably mindless dreaming but if we loved again, I swear I'd love you right",
			"I'd go back in time and change it but I can't. So if the chain is on your door I understand",
			"She wears high heels, I wear sneakers. She's cheer captain, And I'm on the bleachers",
			"Dreaming about the day when you wake up and find that what you're looking for has been here the whole time",
			"Well count to ten, take it in. This is life before you know who you're gonna be",
			"And you know I wanna ask you to dance right there. In the middle of the parking lot...Yeah",
			"And I don't know how it gets better than this. You take my hand and drag me head first... Fearless",
			"And I don't know why but with you I'd dance, in a storm in my best dress... Fearless",
	};
	
	public static String getRandomShortLyric(){
		return shortlyrics[(int)Math.floor(Math.random() * shortlyrics.length)];
	}
	
	public static String getRandomLyric(){
		return lyrics[(int)Math.floor(Math.random() * lyrics.length)];
	}
	
}
