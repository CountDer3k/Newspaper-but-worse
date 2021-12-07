INSERT INTO `User` (username, `password`, email, first_name, last_name, created_on, modified_on) 
VALUES
	("maryjane42", "$2a$10$tk16Rq5RGHIV0Oamu5J6pOmwb2w40Sdk3BZ8uNaiuI25Fs.QHFm7S", "mjwatson@gmail.com", "Mary Jane", "Watson", now(), now()),
	("SymbioticBrock", "$2a$10$AzKDkZRSHQJQOwpafZ6ok.pMqdc3wtv8hU.KtAsvlpBspIxEnbn22", "ecabrock@gmail.com", "Edward", "Brock", now(), now());
	
INSERT INTO User_Role (user_id, role_id)
VALUES
	((SELECT user_id FROM `User` WHERE username = "maryjane42" LIMIT 1), 2),
    ((SELECT user_id FROM `User` WHERE username = "SymbioticBrock" LIMIT 1), 3);
    
 INSERT INTO Section (section_id, section_name)
 VALUES
 	(1, "News"),
 	(2, "Local"),
 	(3, "Sports"),
 	(4, "Feature"),
 	(5, "Business"),
 	(6, "Editorial");
    
 INSERT INTO Post (create_on, modified_on, user_id)
 VALUES
 	(now(), now(), 1),
 	(now(), now(), 4),
 	(now(), now(), 2),
 	(now(), now(), 3);
 	
 INSERT INTO Article (post_id, title, content, access, section_id)
 VALUES
 	(1, "Coney Island to Spider-Man: Bug Off, Webhead!", "Coney Island re-opened its historic gates today, years after a devastating attack by Spider-Man forced the park and adjacent beach to close to the public. This resilient Brooklyn neighborhood has put on a brave face after the onslaught brought on them by the webbed menace, where a plane crashed into their family amusement park one lonely night. While local officials refused to name Spider-Man directly in their complaint - preferring to blame the working-class man, Adrian Toomes - we at the Bugle believe that this is only due to fear of payback by a vindictive, dangerous Spider-Man. We tried to warn every one of his true colors, even before our big reveal last week, and in case you live under a rock, you can read all about the unmasking in last week's issue. It’s ok, you gave him the benefit of the doubt with his whole red and blue outfit, but we hope now you will listen. We also hope all Bugle readers will join us in supporting Coney Island’s big re-opening and telling Spider-Man what we all should’ve told him a long time ago: BUG OFF, WEBHEAD!", "FR", 1),
	(2, "Bombshell Exclusive: Spider-Man Unmasked!", "We’ve been reporting for years that he has been a threat to our city. In recent events, he brought ruin on our European allies. Now, the time has come for Spider-Man to face the harsh light of truth. And there’s no one more qualified to shine that light than the Daily Bugle. Today, we can exclusively report the identity of the criminal vigilante who has stalked our streets and assembled a web of lies to cover up his misdeeds. And our source is as impeccable as they come: the intergalactic savior and martyr, Mysterio. And in one sentence, recorded just before his tragic death, Quentin Beck has saved the world once again. That sentence? “Spider-Man’s real name… is Peter Parker.” That’s right, Buglers! Spider-Man isn’t even a “man” at all - he’s a little boy! Is this the kind of person our society should be venerating as a “superhero?” A snot-nosed teenager who thinks he can endanger the public just to show off for his latest crush!? We reached out to some of the teachers at Midtown High School for more background on their student, Peter Parker, but they declined to comment. The Daily Bugle calls on the city of New York - nay, the entire Nation at large - to band together in defiance of this false hero. It’s time we told this entitled next generation to sit down and let the adults in the room save the world!", "FR", 4),
	(3, "A True Hero: The Magnificent Mysterio", "He died so the truth could live. He was not born on Earth - at least, not our Earth - but he gave his life protecting it and this video is all the proof we need. If only the people weren’t fed such lies about him. If only the so-called superhero charged with helping him didn’t betray him instead. As authorities no doubt begin their search for the murderer known as Spider-Man, the Bugle wants to take a moment to thank Mysterio, A.K.A. Quentin Beck for the little time we had with this magnificent hero. The details of his life before becoming a hero are understandably thin. As the so-called Elementals came to our Earth, they attacked in Mexico, Italy, and the Czech Republic - each time being defeated by Mysterio in spectacular fashion. He was as handsome as he was powerful - and mysterious, to boot! Thus, the name! Had the world finally found a new superhero into which to put their hard-won trust? Yes, but it was not to be. The final Elemental attack, which touched down in London, presented Mysterio’s ultimate challenge. The exclusive video provided to the Bugle is evidence he was stabbed directly in the back by the hero he believed to be his friend: that masked menace, Spider-Man. In his final moments, Mysterio once again stood for truth and justice in a world gone mad, warning the world that Spider-Man was using dangerous technology to con his way into the hearts and minds of the world. He also provided the Bugle a lead to an even bigger story, we are still working on deciphering the encryption, but the details could reveal an earth-shattering consequence, which we will soon publish! Watch this space! In the end, Mysterio was not simply the greatest superhero of all time… but he was also the second-most important correspondent in Daily Bugle history (second only to J. Jonah Jameson himself, of course). So thanks for the scoop, Mysterio - and Rest in Peace.", "FR", 6);
	
INSERT INTO Comment (post_id, content, parent_id)
	VALUES (4, "Go get 'em, tiger!", 1);
 	