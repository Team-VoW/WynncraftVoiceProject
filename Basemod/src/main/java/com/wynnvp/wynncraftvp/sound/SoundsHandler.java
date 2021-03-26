package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;

public class SoundsHandler {
    // public static SoundEvent TALKING_MUSHROOM_RETURNINGARDFD;
    public HashMap<String, CustomSoundClass> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();
        registerSounds();
    }

    public void registerSounds() {
        //Talking Mushroom
        addSound("[1/1] Talking Mushroom: PRICK.", "talkingmushroomprick", true);
        addSound("[1/2] Talking Mushroom: IF I HAD A MOVABLE NECK, I'D BE TWISTING IT AROUND TO FACE YOU WITH UTTER CONFUSION.", "talkingmushroomragnioutskirts", true);
        addSound("[1/1] Talking Mushroom: WHAT IN THE NAME OF ALL THAT IS HOLY IS GOING THROUGH YOUR HEAD?! WE'RE MILES FROM THE SKY ISLANDS!! WHERE IS THIS PLACE?!", "talkingmushroomragni", true);
        addSound("[1/2] Talking Mushroom: ... STAIRWAY TO HEAVEN, HUH? I DON'T KNOW ABOUT YOU, BUT I DON'T GET THE APPEAL.", "talkingmushroomtherck", true);
        addSound("[1/3] Talking Mushroom: OH, HEY, THIS PLACE IS NICE! IT HAS EVERYTHING A PLACE COULD NEED! MUSHROOMS, COZY HOUSES, BEINGS OF GREAT POWER...", "talkingmushroomelkurn", true);
        addSound("[1/1] Talking Mushroom: SO, WHAT IS THIS PLACE? IS IT A BRIDGE, OR IS IT A TOWN? MAKE UP YOUR DAMN MIND, VILLAGERS!", "talkingmushroombucie", true);
        addSound("[1/1] Talking Mushroom: WOW, REAL GRAND CITY HERE, HUH? THIS PLACE IS HUGE! AND PROBABLY A MESS TO NAVIGATE, REALLY POORLY DESIGNED IF YOU THINK ABOUT IT.", "talkingmushroomllevingar", true);
        addSound("[1/3] Talking Mushroom: ... WOW. I'M... I'M ACTUALLY SHOCKED.", "talkingmushroomforbiddenprison", true);
        addSound("[1/3] Talking Mushroom: HEY, THIS PLACE HAS GOT SOME NICE LOOKING CAPS! DROP ME OFF HERE!", "talkingmushroomyahya", false);
        addSound("[1/2] Talking Mushroom: WAIT, I'VE HEARD OF THIS PLACE. TIME GOES SLOWER HERE, DOESN'T IT?", "talkingmushroomdooroftime", true);
        addSound("[1/1] Talking Mushroom: WHY IS IT SO HOT IN HERE? OH, YOU HAD BETTER NOT BE THINKING WHAT I THINK YOU'RE THINKING! I'M NOT GONNA BE A ROASTED MUSHROOM TODAY!", "talkingmushroommountwynn", true);
        addSound("[1/2] Talking Mushroom: WOAH, THAT THING LOOKS REALLY WEIRD. IS THIS IMPORTANT OR SOMETHING? YEAH, THIS HAS TO BE IMPORTANT!", "talkingmushroomendportal", true);
        addSound("[1/1] Talking Mushroom: I KNOW THESE CANYON EGGS GET LARGE BUT I HAVE TO ASK, HOW DID THE BIRD FIT IN HERE TO MANAGE THIS?!", "talkingmushroommesacanyoneggs", true);
        addSound("[1/8] Talking Mushroom: Y-YOU CAN'T BE SERIOUS. YOU... YOU SHOULDN'T BE HERE. I LOST THAT KEY A LONG TIME AGO, BUT SOMEHOW... YOU FOUND IT AND MADE YOUR WAY HERE. RIGHT, LET ME EXPLAIN.", "talkingmushroombackstorry", true);
        addSound("[1/2] Talking Mushroom: HAHA, LOOK! YOU'RE PRACTICALLY ONE OF THEM, YOU FIT RIGHT IN! NOW STAY HERE LIKE A GOOD ANIMAL AND LET ME GO MY OWN WAY.", "talkingmushroomoutofmymind", true);
        addSound("[1/1] Talking Mushroom: A TREE GUARDIAN? SERIOUSLY?! THAT'S ABOUT AS BELIEVABLE AS A TALKING MUSH...OH WAIT. HUH. CARRY ON.", "talkingmushroomderneltreeguardien", true);
        addSound("[1/1] Talking Mushroom: WAIT, YOU'RE ACTUALLY FALLING FOR THIS STUFF? SERIOUSLY?! IT'S CLEARLY JUST A BUNCH OF SEEDS PAINTED GOLD, ARE YOU QUITE LITERALLY BLIND?", "talkingmushroomzhight", true);
        addSound("[1/4] Talking Mushroom: ...AND I HAD THOUGHT SOME OF YOUR PREVIOUS LOONEY ADVENTURES HAD INDICATED YOU WERE A BUNBLING IDIOT, BUT YOU'VE REALLY OUTDONE YOURSELF... WHERE EVEN ARE WE?!", "talkingmushroombaabsabondedmines", true);
        addSound("[1/1] Talking Mushroom: HEY HEY HEY, WHERE DO YOU THINK YOU'RE GOING? THERE'S A BOUNTY ON MY HEAD HERE SINCE... THE INCIDENT... CAN YOU JUST AVOID THIS PLACE LIKE YOU AVOID MY ADVICE?", "talkingmushroomfrumagate", true);
        addSound("[1/2] Talking Mushroom: OH NO. WHAT DID THAT BUTTON DO? WHAT DID THE BUTTON DO? DID YOU BREAK IT? I BET YOU BROKE IT!", "talkingmushroomheartoflevingar", true);
        addSound("[1/2] Talking Mushroom: I'M GONNA BE STUCK WITH YOU FOREVER, AREN'T I? MAY AS WELL THROW ME IN ONE OF THOSE GRAVES RIGHT NOW!", "talkingmushroomgeilboardcemetary", true);
        addSound("[1/2] Talking Mushroom: OI, WHY'RE YOU TAKING ME ALL THE WAY OUT HERE? MY COUSIN LIVES HERE, I HATE HIM!", "talkingmushroomtrippyforest", true);
        addSound("[1/1] Talking Mushroom: *GASP* *PANT* IS THERE ZERO AIR UP HERE? GET ME DOWN THIS INSTANT! OR IS YOUR BRAIN SO TINY IT DOESN'T NEED OXYGEN TO SURVIVE?!", "talkingmushroomflightindistress", true);
        addSound("[1/2] Talking Mushroom: HEY NUMBSKULL, LEAVE ME HERE, ALRIGHT? I SAW SOME NICE CAPS OVER THERE.", "talkingmushroommushroomcave", true);
        addSound("[1/2] Talking Mushroom: BRILLIANT. ABSOLUTELY BRILLIANT! HOW BRAINDEAD ARE YOU?! THERE'S NOTHING HERE!", "talkingmushroompsiloshouse", true);
        addSound("[1/1] Talking Mushroom: WHO KNEW BEING EATEN ALIVE BY A FIRE-BREATHING DRAGON IS BETTER THAN BEING STUCK WITH YOU?", "talkingmushroomozothgut", true);
        addSound("[1/1] Talking Mushroom: URGH, WHAT IS THAT AWFUL STENCH?! IT'S ALMOST OVERPOWERING YOUR OWN RANCIDNESS! I MEAN, DO YOU EVER BATHE?!", "talkingmushroomcanarycallscave", true);
        addSound("[1/3] Talking Mushroom: ARGH, I'M CURSED, I SWEAR IT! EVEN THE SWEET RELEASE OF DEATH HASN'T GOTTEN ME AWAY FROM YOU! YOU'RE LIKE A COCKROACH!", "talkingmushroomdeathrealm", true);
        addSound("[1/2] Talking Mushroom: YOU KNOW WHAT, TO HECK WITH THIS! I'M BAILING, SEE YA LATER! HAVE A TERRIBLE LIFE, YOU INGRATE!", "talkingmushroommoltenheights", true);
        addSound("[1/1] Talking Mushroom: HOW DID YOU MANAGE TO GET ME TO SOMEPLACE DARKER THAN THE INSIDE OF YOUR SATCHEL? WHAT, DID YOU CRAWL UP SOMETHING'S-", "talkingmushroomfantasticvoyage", true);
        addSound("[1/1] Talking Mushroom: OH GREAT, ANOTHER ONE. JUST FANTASTIC. WHAT'S WITH THE FACE, HUH? NEVER SEEN A TALKING MUSHROOM BEFORE? GO AWAY!", "talkingmushroombeforerecipefordisaster", false);
        addSound("[1/2] Talking Mushroom: OH MY, LOOK WHO'S BACK. HERE I THOUGHT I WAS FREE OF YOUR GRASP AND THEN YOU COME BACK HERE.", "talkingmushroomafterrecipefordistaster", false);
        addSound("[1/2] Talking Mushroom: HEY DWEEB, LOOK OVER THERE! I THINK IT'S... PEOPLE?", "talkingmushroomonethousandmetersunder", true);
        addSound("[1/1] Talking Mushroom: HEY MEATBAG, DON'T YOU THINK YOU'RE A LITTLE FAT FOR THAT THING? WAIT, YOU'RE SERIOUS? STO-AAAHHH!!!", "talkingmushroomcorkuscity", true);
        addSound("[1/1] Talking Mushroom: HUH, THIS VIEW IS GOOD. PSST... I DON'T WANT TO ACKNOWLEDGE THE FACT THAT WE'RE STANDING ON A CLOUD IN FEAR OF IT FALLING APART LIKE SOME CLICHÉ... GOOOOOD VIEW.", "talkingmushroomcorkuscloud", true);
        addSound("[1/1] Talking Mushroom: I BET THE PERSON WHO LIVES HERE DIDN'T EVEN COME UP WITH THIS ROBOT. IT'S TOO GOOD TO BE ORIGINAL.", "talkingmushroomcorkusbastion", true);
        addSound("[1/3] Talking Mushroom: HEY IDIOT, WHERE ARE WE THIS TIME! IT FEELS ALL... BRIGHT, ALMOST EXCESSIVELY SO-", "talkingmushroomnexusoflight", true);
        addSound("[1/3] Talking Mushroom: YOU KNOW, HAVE I EVER TOLD YOU JUST HOW MUCH I HA-", "talkingmushroomrolportal", true);
        addSound("[1/3] Talking Mushroom: SATISFIED YET? CAN YOU TRY UNHINGING HIS JAW AND PUTTING YOUR ARM IN THERE?", "talkingmushroomfeathersflydog", true);
        addSound("[1/1] Talking Mushroom: HEY IDIOT, WHERE ARE WE?? THIS PLACE SMELLS LIKE-", "talkingmushroommonumenttosifried", true);
        addSound("[1/3] Talking Mushroom: WOW. NOW THAT IS A BIG GUY. IT LOOKS ANCIENT! I WONDER WHO COULD'VE BUILT SOMETHING LIKE THIS...", "talkingmushroomtcc", true);
        addSound("[1/4] Talking Mushroom: ... WOW. I'M SPEECHLESS. HUMAN, ARE YOU SEEING THIS PLACE? WITH THE NUMBER OF EMERALDS HERE... WE'RE GONNA BE RICH!!", "talkingmushroomabondedmines", true);
        addSound("[1/2] Talking Mushroom: ... ALRIGHT. I'VE ACCEPTED THAT YOU GO TO WEIRD PLACES BUT THIS CROSSES THE LINE. WHERE EVEN ARE WE?", "talkingmushroomeyeballforest", true);
        addSound("[1/4] Talking Mushroom: GREAT JOB! YOU SOMEHOW MANAGED TO FALL OUT OF REALITY BY DOING ABSOLUTELY NOTHING!", "talkingmushroomotherworldyoccurence", true);
        addSound("[1/5] Talking Mushroom: ALRIGHT, HOLD UP, LET ME GET SOMETHING STRAIGHT HERE. YOU ACTUALLY THINK IT'S A GOOD IDEA TO GO INSIDE THAT OMINOUS DOME THAT'S LITERALLY ERUPTING WITH THIS HORRIBLE DARK SENSATION FOR... WHAT REASON??", "talkingmushroombeforeeyefight", true);
        addSound("[1/3] Talking Mushroom: OH. OH, I DON'T- I DON'T LIKE THIS PLACE. AT ALL. WH-WHERE ARE WE??", "talkingmushroombotomlesspit", true);
        addSound("[1/1] Talking Mushroom: WOAH, HEY, WH-WHAT ARE WE DOING HERE, HUMAN?? IT'S... IT'S PROBABLY DANGEROUS IN THERE! Y-YOU WOULDN'T WANT TO DIE, WOULD YOU???", "talkingmushroomsecretariodopassage", true);
        addSound("[1/3] Talking Mushroom: WOW, LOTS OF POWERFUL MAGIC HERE, HUH?? MUCH STRONGER THAN ANYTHING YOU COULD EVER DO, THAT'S FOR SURE!", "talkingmushroomhouseoftwain", true);
        addSound("[1/2] Talking Mushroom: OH, LOOK AT THESE THINGS. THEY'RE KIND OF CUTE. A LITTLE BIT TOO CUTE... IT'S A CONSPIRACY, I TELL YOU!", "talkingmushroomwybelisland", true);
        addSound("[1/3] Talking Mushroom: I KNEW IT! IT'S ALL A CONSPIRACY!", "talkingmushroomorangewybelactor", true);
        addSound("[1/1] Talking Mushroom: THIS BRIDGE SEEMS DIFFERENT SINCE YOU LAST CROSSED IT, OR IS IT JUST ME? IT MIGHT JUST BE YOUR 247TH TIME CROSSING IT THAT'S DISORIENTATING ME… OR DID I LOSE COUNT AGAIN?", "talkingmushroomnivadetlasbridge", true);
        addSound("[1/2] Talking Mushroom: WOAH, WAIT. WHERE ARE WE? WHAT ARE YOU DOING HE-", "talkingmushroomhiddencamp", true);
        addSound("[1/3] Talking Mushroom: ...I DON'T KNOW WHAT MANIC IDEA YOU'RE ABOUT TO HAVE NOW, BUT I'M GOING TO PUT AN END TO IT RIGHT NOW. YOU DO NOT WANT TO GO IN THERE.", "talkingmushroomoutsideeo", true);
        addSound("[1/3] Talking Mushroom: REALLY COOL CITY, HUH? BUILT UP ON THE CLIFFS, VERY STYLISH, IS THAT WHAT YOU'RE THINKING?", "talkingmushroomkandon", true);

        //Creeper Infiltration
        addSound("[1/7] Thomas: Oh, finally, someone has come to this hidden village!", "thomas_creeperinfl_1", false);
        addSound("[1/10] Thomas: I didn't expect you to fall out of the sky like that. Are you okay, or should I go get the potion merchant to heal you?", "thomas_creeperinfl_2", false);
        addSound("[1/6] Thomas: I see you have the hide. This skin will work perfectly for my plan.", "thomas_creeperinfl_3", false);
        addSound("[1/7] Thomas: So, what was in the cave? Were there really creepers in there?", "thomas_creeperinfl_4", false);

        //Maltics Well
        addSound("[1/4] Rynend: Please help us! We called for a Ragni guard days ago, but Humans tend to forget this little village.", "malticwellrysend1", false);
        addSound("[1/1] Child: Help me! Someone! The witch is being mean!!", "malticwellchild", true);
        addSound("[1/2] Witch: I don't know why they call me a witch. Magic is everywhere, you know. This one's father made fun of my looks, so I'll turn his son into a Grook!", "malticwellwitch1", false);
        addSound("[1/2] Witch: Oh...drat. I never could get that spell right. Well, now you'll have to guess the way, um, you fool! Turn back now, for, uh...", "malticwellwitch2", false);
        addSound("[1/4] Witch: Oh...didn't think that'd fool you. You know, I'm really not all that good at magic. I just like wearing pointy hats, and have warts.", "malticwellwitch3", false);
        addSound("[1/3] Rynend: Oh my son! What happened down there? Erh, not a witch, you say?", "malticwellrysend2", false);

        //Grave Digger
        addSound("[1/8] Sayleros: You sure look like a strong adventurer who could help me!", "gravediggersayleros1", false);
        addSound("[1/2] Drucksh: Mmh. g... uh! oh, why hello there.", "gravediggerdrucksch1", false);
        addSound("[1/4] Drucksh: Ohh mmhhhgg... g-g-good old Nemract whiskey!", "gravediggerdrucksch2", false);
        addSound("[1/4] The Priest: Are you trying to go down into the crypt? Oh, that is a very bad idea.", "gravediggerpriest1", false);
        addSound("[1/2] The Priest: You got the flesh. Good.", "gravediggerpriest2", false);
        addSound("[1/1] Sayleros: Brilliant! It is a little bit dusty, but it will do the trick! Here, take these as proof of my gratitude.", "gravediggersayleros2", false);

        //Wrath of the Mummy
        addSound("[1/6] Ormrod: Uh, you shouldn't have come here!", "wrathofthemummyormrod1", false);
        addSound("[1/6] Achper: If I know about mummies? Of course! I've spent my entire life studying them!", "wrathofthemummyachper", false);
        addSound("[1/3] Bandit: Hey, what are you doing? If you're trying to loot the tomb, me and the other Creden Tibus already beat you to it.", "wrathofthemummybandit1", false);
        addSound("[1/4] Bandit: Well, whaddya know? You got some Pink Wool! Okay, hand it here and I'll look the other way and let you into the tomb.", "wrathofthemummybandit2", false);
        addSound("[1/4] Ormrod: Uh, hello again... What are those bandages?", "wrathofthemummyormrod2", false);

        //Corrupted village
        //FIX TYPO WYNNN!!
        addSound("[1/17]roy:well,thatdidthetrick.let'stakealook.", "corruptedviallgeroy", false);
        addSound("[1/7] Orikal: Go away! I know you're angry but there's nothing to gain her-", "corruptedviallgeorikal1", false);
        addSound("[1/6] Orikal: That was quick... did you find anything?", "corruptedviallgeorikal2", false);
        addSound("[1/5] Orikal: Okay. I happen to know a recipe for creating an extremely lethal magical explosive.", "corruptedviallgeorikal3", false);
        addSound("[1/3] Orikal: You got 'em all! Excellent, this is exactly what we need.", "corruptedviallgeorikal4", false);
        addSound("[1/4] Orikal: Oh, do you feel that? It's like a buzzing in my ears I never knew about has just vanished.", "corruptedviallgeorikal5", false);
        addSound("[1/3] Alfonse: This is insane, why are we here?! We're looking for corruption you know that right?", "corruptedviallgealfonse", true);

        //Ice nations
        addSound("[1/6] Adigard: Welcome traveler, to our humble island...", "icenationsadigard1", false);
        addSound("[1/7] Adigard: I should have expected that. Hallfred was always very greedy...", "icenationsadigard2", false);
        addSound("[1/5] Hallfred: Welcome traveler! What brings you on our island?", "icenationshallfred1", false);
        addSound("[1/4] Hallfred: Incredible! He actually delivered the treasure!", "icenationshallfred2", false);

        //Lost Royalty
        addSound("[1/5] Yavlis: Oh, hello there! I had hoped not to bother you, adventurer, but this is urgent.", "lostroyaltyyalvis1", false);
        addSound("[1/5] Yavlis: Ah, finally! You're back. Did they let you bring him back? Those mercenaries have an awful habit of failing to honour their deals.", "lostroyaltyyalvis2", false);
        addSound("[1/7] Mercenary Leader: Excuse me?! What are you doing here, how did you find our secret hideout?", "lostroyaltymercenary1", false);
        addSound("[1/3] Mercenary Leader: You've gotten the talisman! I can't believe you actually got through that puzzle!", "lostroyaltymercenary2", false);
        addSound("[1/4] Prince of Troms: Uh... Hi? Err, why did you free me? I'm trying to run away, I hate being the son of the king.", "lostroyaltykingsson", false);

        //Wynn Excavation site A
        addSound("[1/4] Vade: Oh, hey kid. Wanna make some money?", "wynnexcvationavade1", false);
        addSound("[1/8] Tesha: Hello, welcome to WynnExcavation Labs!", "wynnexcvationatesha", false);
        addSound("[1/6] Vade: Who are you?", "wynnexcvationavade2", false);
        addSound("[1/4] Vade: Hey kid, did you just come from the tomb?", "wynnexcvationavade3", false);

        //Wynn Excavation site B
        addSound("[1/1] Flendar: We know what you stole from us in the desert! Take THIS! You won't be escaping this dead end any time soon!", "wynnexcvationbflendar", true);
        addSound("[1/6] Excavator Lykron: Good day! You're the person that helped us out in the desert aren't you? Excellent! Just the person I need! Can you help me out with a problem I have?", "wynnexcvationblykron1", false);
        addSound("[1/5] Excavator Lykron: I have to admit, I'm impressed. I genuinely thought our plan would be successful. Who knew that dead end had a secret exit.", "wynnexcvationblykron2", false);
        addSound("[1/7] WynnExcavation Archaeologist: Baffling... What a puzzling situation I am in...", "wynnexcvationbarchaeologist", false);

        //Wynn Excavation site C
        addSound("[1/8] Excavator Placardus: All operations functional… Transportation on schedule… Oh. It's you. We've been looking for you.", "wynnexcavationcplacardus1", false);
        addSound("[1/7] Excavator Placardus: You couldn't just take my polite word and leave it at that, could you?", "wynnexcavationcplacardus2", false);
        addSound("[1/3] Guard Klerodor: I'm sorry. This is a private meeting.", "wynnexcavationcguard", false);
        addSound("[1/4] Chief Excavator Dranfor: Hello and welcome, senior members of Wynn Excavation Site C. May the dark's gaze forever watch over us. I want to update you on our progress thus far. The fire relic crystal has been largely intact in the heart of the volcano for many years.", "wynnexcavationcdranfor1", true);
        addSound("[1/1] Chief Excavator Dranfor: The alarm has been set off! There is an unauthorized guest! Guards, seize them!", "wynnexcavationcdranfor2", true);
        addSound("[1/10] ???: You. You are the one they are worried about.", "wynnexcavationcamadel", false);

        //Wynn Excavation Site D
        addSound("[1/8] Royal Advisor Carlos: His majesty Lord King of Troms is indisposed at this moment.", "wynnexcavationcarlos1", false);
        addSound("[1/1] Royal Advisor Carlos: Oh my, a secret passage has opened up beneath the throne! Quickly, head inside!", "wynnexcavationcarlos2", false);
        addSound("[1/9] King of Troms: I am impressed you managed to find me. Someone of my intellect is not normally found when he hasn't the intention of being so.", "wynnexcavationkingoftroms", false);
        addSound("[1/6] Troms Guard: Time is short, so I'll have to spare you the details, but let's just say I'm on your side.", "wynnexcavationtromsguard", false);
        addSound("[1/4] ???: Hey, psst. Come here!", "wynnexcavationquestionmark", false);
        addSound("[1/4] Traitor Thomas: I'm sorry, I can't let you in there.", "wynnexcavationthomas1", false);
        addSound("[1/4] Traitor Thomas: Is that… The map fragments? You found them!", "wynnexcavationthomas2", false);
        addSound("[1/8] Traitor Amadel: Welcome, friend. I knew you would figure it all out.", "wynnexcavationamadel1", false);
        addSound("[1/1] Traitor Buice: WynnExcavation have done really horrible things, and I used to help...", "wynnexcavationbucie", false);
        addSound("[1/1] Traitor Jaynar: WynnExcavation will be destroyed, no matter what!", "wynnexcavationjaynar", false);
        addSound("[1/8] Excavator Admin Uci: Oh my. It's you. I can't say I'm surprised to see you, we thought you might be stupid enough to come here.", "wynnexcavationadminuc", false);
        addSound("[1/1] Excavator Sean: My boss Uci is a bit of a mean guy, but hey the perks of minionship aren't bad!", "wynnexcavationsean", false);
        addSound("[1/1] WynnExcavation Scientist Azure: This leads deeper into this dungeon, but it seems like it has to be activated from the other side", "wynnexcavationazure", false);
        addSound("[1/8] WynnExcavation Leader Amadel: Well, well. Looks like you've finally made it.", "wynnexcavationamadel2", false);
        addSound("[1/1] WynnExcavation Leader Amadel: Step into this hole, and accept your fate. It's over, you've lost!", "wynnexcavationamadel3", false);
        addSound("[1/2] Amadel's Assistant: I see you have finally shown up.", "wynnexcavationassistant1", false);
        addSound("[1/2] Amadel's Assistant: That was a mere fluke..", "wynnexcavationassistant2", false);
        addSound("[1/2] Amadel's Assistant: He's simply warming up...", "wynnexcavationassistant3", false);
        addSound("[1/2] Amadel's Assistant: I, I can't believe you beat him...", "wynnexcavationassistant4", false);
        addSound("[1/18] Ragni's King: You... hmm... Ahh yes! I remember you. You're one of the recruits I accepted long ago..", "wynnexcavationragnisking", false);
        addSound("[1/1] Corrupted Amadel: The crystals combine the power of the stars themselves.. an ultimate power... the power to reign over the entire WORLD!", "wynnexcavationamadel4", true);
        addSound("[1/1] Shadow Amadel: I- I- I feel... unstopable.. this power... will not allow me to lose..!!!", "wynnexcavationamadel5", true);
        addSound("[1/1] Amadel: DID YOU REALLY THINK YOU COULD WIN?!?! YOU ARE NOTHING COMPARED TO ME! NOTHING!! I WILL EXTERMINATE ALL LIFE, STARTING WITH YOU!! NOW, PERISH!!!", "wynnexcavationamadel6", true);

        //Mushroom man
        addSound("[1/6] Yahya: G-... Hi! What a coincidence, I... I actually need you, yes.", "mushroomman1", false);
        addSound("[1/3] Yahya: Oh, it's you. What? I can't just have one? I-... I'm pretty sure I need a few more than that.", "mushroomman2two", false);
        addSound("[1/5] Yahya: You, again? Oh, you have the.. mushrooms.", "mushroomman3three", false);
        addSound("[1/6] Yahya: H-...Hey! I didn't expected you to find it... Haha... Um...", "mushroomman4four", false);

        //Cluck Cluck
        addSound("[1/6] Nohno: Hey, you aren't a chicken! Wait, you're a human! You can help me! Maybe you are the guy who helped my brother Yahya, but who knows, you humans all look the same to me.", "cluckclucknohno1", false);
        addSound("[1/2] Nohno: What is this?! His feather? What did you do to Cluckles?! I wanted you to take care of him, not kill him!", "cluckclucknohno2", false);

        //Infested Plants
        addSound("[1/5] Ope: Hello! Young traveler.", "infplants1", false);
        addSound("[1/3] Ope: I've literally been sitting here for hours, maybe not hours, at least five minutes!", "infplants2", false);

        //Enzans Brother
        addSound("[1/6] Therck: Excuse me? Can't you see I'm busy?", "enzansbrothertherck1", false);
        addSound("[1/7] Therck: Ah, you're back! Do you have the mushroom?", "enzansbrothertherck2", false);
        addSound("[1/5] Enzan: I see you want to venture into the wilderness!", "enzansbrotherenzan", false);

        //Reincarnation
        addSound("[2/9] Bob: I am Bob, master of the arts of Wynn.", "reincarnationbob", true);
        addSound("[1/13] Batelu: Hello adventurer! I see you want to solve the mystery of Bob's tomb also? Glad to meet you.", "reincarnationbatleu1", false);
        addSound("[1/2] Batelu: Wonderful! We can now open the passage to the true tomb of Bob...", "reincarnationbatleu2", false);
        addSound("[1/5] Batelu: Bob's Seal of Approval? Oh my goodness!", "reincarnationbatleu3", false);

        //Bobs lost soul
        addSound("[1/10] Wedyf: Welcome traveler. Are you looking for something?", "bobslostsoulwedyf1", false);
        addSound("[1/6] Wedyf: Great, you got everything.", "bobslostsoulwedyf2", false);
        addSound("[1/6] Wedyf: I'm glad to see you again.", "bobslostsoulwedyf3", false);
        addSound("[1/6] The Blacksmith: What can I do for ya?", "bobslostsoulblacksmith1", false);
        addSound("[1/4] The Blacksmith: ... Hi again. I see you got the weapons.", "bobslostsoulblacksmith2", false);
        addSound("[1/9] Tarod: You're one of those soldier types, right? My name is Tarod, and I could really use your assistance here.", "bobslostsouldtarod1", false);
        addSound("[1/6] Tarod: Welcome back. Did you find anything worthwhile?", "bobslostsouldtarod2", false);
        addSound("[1/3] Tarod: You're back... Has he... has he finally been put to rest?", "bobslostsouldtarod3", false);
        addSound("[1/6] Apprentice: Weapons of the depressed kind? Oh, of course, of course, I know of the set! Around Nesaak, I hear they are kept.", "bobslostsoulapprentice", false);

        //Temple of Legends
        addSound("[1/10] Garull: GWUH! Oh god, don't scare me like that! Who... Who even are you?!", "templeoflegendsgarull1", false);
        addSound("[1/4] Garull: Thank you for listening to me about not being so quiet coming over here. I'm guessing you've got everything?", "templeoflegendsgarull2", false);
        addSound("[1/6] Jorkin: You may want to get out of here, traveller. The Nether is very dangerous!", "templeoflegendsjorkin1", false);
        addSound("[1/7] Jorkin: You have the Light Dust, then? Hand it here, I want to see something.", "templeoflegendsjorkin2", false);
        addSound("[1/5] Kelight: I see you want to enter this mighty temple! Do not deny it, I see it in your eyes.", "templeoflegendskelight1", false);
        addSound("[1/6] Kelight: Mhm, mhm. Yeah, that was a bit fast. Fork them over, I need to see if these are the real deal.", "templeoflegendskelight2", false);
        addSound("[1/10] Kelight: Back so soon? A little bit suspicious. Allow me to examine your trophies once more.", "templeoflegendskelight3", false);
        addSound("[1/6] Kelight: Hah. Took you long enough. This is the light dust, I presume?", "templeoflegendskelight4", false);
        addSound("[1/4] Kelight: Oh, so you actually managed to find the blighter. I was getting tired of waiting for you.", "templeoflegendskelight5", false);
        addSound("[1/4] Kelight: .. You... you did it? H-how?!", "templeoflegendskelight6", false);
        addSound("[1/6] Rayshyroth: Oh? Well, greetings. What brings you to our quiet lonely island, let alone all the way up here?", "templeoflegendsrayshyroth1", false);
        addSound("[1/9] Rayshyroth: Aha, you're back with the Sky Vapor! Sorry about the climb, even I find it rather inconvenient at times.", "templeoflegendsrayshyroth2", false);

        //Cook Assistant
        addSound("[1/4] The Cook: I can't believe what is happening to me!", "cookassistant1", false);
        addSound("[1/3] The Cook: Great! You got all the ingredients!", "cookassistant2", false);

        //Clearing the Camps
        addSound("[1/6] Captain Kymer: Aha, another Ragni soldier, eh? Well, a warm welcome to you.", "clearingthecampskymer1", false);
        addSound("[1/5] Captain Kymer: Ah, the soldier from earlier! You're back, I see. Any luck defeating those- ...yeesh. Are those their heads?", "clearingthecampskymer2", false);

        //The Sewers Of Ragni
        addSound("[1/4] Jenprest: Soldier! Good timing. We've been requesting help for ages.", "thesewersofragnijenprest1", false);
        addSound("[1/3] Jenprest: Ah you're already here. Coming up behind you soldier!", "thesewersofragnijenprest2", false);
        addSound("[1/3] Jenprest: Do you feel like there's something lurking that doesn't want us here?", "thesewersofragnijenprest3", false);
        //FIX TYPO WYNNN!! NOT WORKING
        addSound("[1/3] Jenprest: Ah, you're alive! Looks like we underestimated the amount of sewage.", "thesewersofragnijenprest4", false);
        addSound("[1/1] Jenprest: Oh no, oh no no no! Did you just open that? Come here immediately.", "thesewersofragnijenprest5", false);
        addSound("[1/6] Jenprest: Oh no...", "thesewersofragnijenprest6", false);
        addSound("[1/1] ???: Release me...", "thesewersofragniquestionmark", false);

        //Decrepit Sewers
        addSound("[1/1] ???: You dare enter these sewers, human? These pipes once hid the civilians of Ragni during raids... Now it just holds corpses.", "decriptsewerswitherehead1", true);
        addSound("[1/1] ???: The forces of the Humans could not save me when I hid here, and they can't save you either.", "decriptsewerswitherehead2", true);
        addSound("[1/1] Witherhead: You persist through the sludge, now you will join me like the rest!", "decriptsewerswitherehead3", true);

        //Star Thief
        addSound("[1/1] ???: It's... mine.", "starthiefthief1", true);
        addSound("[1/1] Thief: .... I can't control myself...fjfjfj This rock...", "starthiefthief2", true);
        //NOT WORKING
        addSound("[1/2] Thief: What is... this feeling… No I won't listen..", "starthiefthief3", false);
        addSound("[1/5] Agent: Hey you! Human. What are you doing here?", "starthiefagent1", false);
        addSound("[1/4] Agent: Hello again. Did you find the meteor down there? Why on earth are you holding some?!", "starthiefagent2", false);

        //Arachnids Ascent
        addSound("[1/8] Enduyn: At ease, soldier! Good timing. We need more manpower.", "archnidsascentenduyn1", false);
        addSound("[1/4] Enduyn: Ah perfect! Now give me that bucket.", "archnidsascentenduyn2", false);

        //The house of twain
        addSound("[1/10] Twendle: Oh, hey you there! Are you heading down this road? You know it's a dead end, right?", "thehouseoftwaintwendle1", false);
        addSound("[1/2] Twendle: I guess I put my faith in the right person. My history lies within these pages, and I don't intend to keep them secret. I shall make them public.", "thehouseoftwaintwendle2", false);

        //One Thousand Meters Under
        addSound("[1/3] The Secretary: Hello, welcome to Ynnos' facility.", "onethousandmetersundersecretary", false);
        addSound("[1/11] Scientist Ynnos: *Ahem* Welcome, everyone.", "onethousandmetersunderscientistyvnoss1", false);
        addSound("[1/10] Scientist Ynnos: You survived! Where is everyone else?", "onethousandmetersunderscientistyvnoss2", false);
        addSound("[1/2] Arlene: I wonder what the mechanic is doing?", "onethousandmetersunderarlene1", false);
        addSound("[1/2] Kantor: Attention crew members.", "onethousandmetersunderkantor1", true);
        addSound("[1/2] Kantor: Thank you so much! I thought we would all die before the mission even started!", "onethousandmetersunderkantor2", false);
        addSound("[1/1] Kantor: Ah! finally! Line up the white rope with the blue blocks. I need to control things from here. Push those blocks into line so we won't just fall down!", "onethousandmetersunderkantor3", false);
        addSound("[1/1] Captain Olof: We have encountered our first island, 100m down. Crew members, climb up the ladder and search the island for the crystal.", "onethousandmetersunderolof1", true);
        addSound("[1/3] Captain Olof: We've arrived at the next void island.", "onethousandmetersunderolof2", false);
        addSound("[1/3] Captain Olof What are these creatures!", "onethousandmetersunderolof3", true);
        addSound("[1/1] Captain Olof: Don't let the creatures break through glass! Press against it and scare them off!", "onethousandmetersunderolof4", false);
        addSound("[1/3] Captain Olof: This is not good, the creatures destroyed part of the windshield.", "onethousandmetersunderolof5", false);
        addSound("[1/2] Captain: Attention crew. We have arrived at the second island, 300m down. We cannot progress without making some emergency repairs.", "onethousandmetersunderolof6", true);
        addSound("[1/5] Captain Olof: We need to repair the submarine.", "onethousandmetersunderolof7", false);
        addSound("[1/3] Captain Olof: Ech! What is this stuff? Well, this should do the trick.", "onethousandmetersunderolof8", false);
        addSound("[1/6] Jesp: Oh, blast! Do you smell that?", "onethousandmetersunderjesp1", true);
        addSound("[1/3] Captain Olof: Attention crew. We have arrived at the next island 700m down.", "onethousandmetersunderolof9", true);
        addSound("[1/5] Rex: We got this, just take care of the monsters while we build the bridge!", "onethousandmetersunderrex1", true);
        addSound("[2/5] Manny: Dont die! We're soon done.", "onethousandmetersundermanny1", true);
        addSound("[3/5] Jesp: This is hard, but we'll make it!", "onethousandmetersunderjesp2", true);
        addSound("[4/5] Arlene: Be careful, it seems to be getting tougher. The bridge is soon ready to use.", "onethousandmetersunderarlene2", true);
        addSound("[5/5] Manny: Hey, the bridge is ready!", "onethousandmetersundermanny2", true);
        addSound("[1/4] Rex: We didn't find the crystal here either. Maybe it just fell in the void?", "onethousandmetersunderrex2", true);
        addSound("[1/13] Celuuse: Whoooooooo are you!?", "onethousandmetersunderceluuse", true);
        addSound("[1/8] Rontaid: WHAT...WHO...Humans in the void!?", "onethousandmetersunderrontaid", true);

        //Recipe for disaster
        addSound("[1/4] Frank: How are ya? Hamsey said you're an old buddy of his, helped him while he was still in Ragni.", "recipefordisastefrank1", false);
        addSound("[1/9] Frank: So you decided to work with me, huh? I promise I won't dissapoint.", "recipefordisastefrank2", false);
        addSound("[1/2] Frank: Well done on getting them items, some of 'em are pretty tough to find!", "recipefordisastefrank3", false);
        addSound("[1/5] Brie: Heya! You must be the adventurer that Hamsey told me about. You know, he was almost ecstatic, but don't tell him I said that!", "recipefordisasterbrie1", false);
        addSound("[1/8] Brie: Heya again! So you decided to make the Zork Stew with me? Hurray!", "recipefordisasterbrie2", false);
        addSound("[1/2] Brie: Oh my gosh, you came back! You were gone for quite a while and I started getting worried about you!", "recipefordisasterbrie3", false);
        addSound("[1/5] Kale: When Hamsey told me about a good friend of his, I didn't expect... this.", "recipefordiasterkale1", false);
        addSound("[1/7] Kale: When Hamsey told me about a good friend of his, I didn't expect... this.", "recipefordiasterkale2", false);
        addSound("[1/3] Kale: Oh, it's you again. You got the ingredients... it's about time!", "recipefordiasterkale3", false);
        addSound("[1/8] Chef Hamsey: Bonjour, let me take you to your seat, monsieu..mada-this way!", "recipefordisasterhamsey1", false);
        addSound("[1/7] Chef Hamsey: Welcome to ma cuisine! C'est beau, non?", "recipefordisasterhamsey2", false);
        addSound("[1/4] Chef Hamsey: Welcome back, mon ami! I see you've return with a dish in hand!", "recipefordisasterhamsey3", false);
        addSound("[1/4] Chef Hamsey: I cannot help but notice that your dish is ready!", "recipefordisasterhamsey4", false);
        addSound("[1/4] Chef Hamsey: I see you've returned, and not empty handed. Please, show me what you've created!", "recipefordisasterhamsey5", false);
        addSound("[1/9] Talking Mushroom: ISN'T THIS JUST ABSOLUTELY FANTASTIC. A HUMAN?! WHO WOULD HAVE GUESSED.", "recipefordisastertalkingmushroom", false);

        //Beyond the Grave
        addSound("[1/4] Death: IT SEEMS I HAVE A NEW GUEST. WELCOME.", "beyondthegravedeath1", true);
        addSound("[1/3] Death: I SUGGEST YOU DON'T EAT ANYTHING THAT YOU ARE SERVED HERE, I HEARD IT IS TO DIE FOR.", "beyondthegravedeath2", false);
        addSound("[1/2] Death: THIS IS AN EMPTY ROOM.", "beyondthegravedeath3", false);
        addSound("[1/1] Death: FEET OFF THE TABLE PLEASE.", "beyondthegravedeath4", false);
        addSound("[1/3] Death: THIS IS THE LIBRARY, WHERE MY GUESTS COME TO SPEND THEIR TIME.", "beyondthegravedeath5", false);
        addSound("[1/4] Death: IT SEEMS YOU REALLY WANTED TO KNOW WHAT WAS BEHIND THIS DOOR.", "beyondthegravedeath6", false);
        addSound("[1/2] Death: ANOTHER DOOR.", "beyondthegravedeath7", false);
        addSound("[1/4] Death: THESE HOURGLASSES SHOW THE LIFESPAN OF HUMANS. WHEN ALL THE SAND HITS THE BOTTOM, I GET A NEW GUEST.", "beyondthegravedeath8", false);
        addSound("[1/1] Death: IF ANYONE ASKS, YES, I AM MARRIED. JUST BECAUSE I DON'T HAVE A HEART DOESN'T MEAN THAT I CAN'T LOVE.", "beyondthegravedeath9", false);
        addSound("[1/4] Death: THIS IS MY OFFICE. GUESS WHAT I DO HERE. HUH?", "beyondthegravedeath10", false);
        addSound("[1/14] Death: I KNOW THIS SEEMS LIKE AN ODD PLACE TO BE. SITTING IN FRONT OF DEATH.", "beyondthegravedeath11", false);
        addSound("[1/2] Death: SOMETHING I'M FINDING INCREASINGLY ANNOYING IS HOW MANY PEOPLE ARE FINDING WAYS TO BECOME IMMORTAL NOWADAYS. INTELLIGENT COMPANY IS STARTING TO GET A BIT SCARCER.", "beyondthegravedeath12", false);
        addSound("[1/5] Death: WELL, I SEE YOU ARE LEAVING.", "beyondthegravedeath13", true);
        addSound("[1/1] Death: STAY AWAY FROM THAT ROOM. IT'S NOT GOOD FOR EITHER OF US.", "beyondthegravedeath14", true);
        addSound("[1/7] Irlok: Welcome to Bantisu Temple. We guide adventuring souls onwards.", "beyondthegraveirlok1", false);
        addSound("[1/5] Irlok: Oh, you've returned. I must admit, I'm very impressed.", "beyondthegraveirlok2", false);

        //Blazing retribution
        addSound("[1/10] Detective Jackson: How can I go about this...? I can't get involved myself, and they won't let me near... Wait, a human?", "blazingretributiondetectivejackson1", false);
        addSound("[1/9] Detective Jackson: I see no axe in your hands, so I should assume neither Sablestone or Loamsprout had it.", "blazingretributiondetectivejackson2", false);
        addSound("[1/4] Detective Jackson: That looks like the axe of Llevigar to me! Blue finish, hot to the touch- well done! What Orc boss had stolen it?", "blazingretributiondetectivejackson3", false);
        addSound("[1/1] ???: GRRRH!! What 'Uthniiazek' mean?!", "blazingretributiongreggr1", true);
        addSound("[1/1] Gregg'r: Uthniiazek! Uthniiazek!! WHY NO LISTEN TO GREGG'R?", "blazingretributiongreggr2", true);
        addSound("[1/3] Gregg'r: Stupid Villager magic thing! Why fire no listen?", "blazingretributiongreggr3", true);
        addSound("[1/7] Piere: Oh sweet baby bovine, my house, what am I going to do? Wait, who are you?", "blazingretributionpiere", false);

        //Potion Making
        addSound("[1/4] Dr. Essren: Ah, you must be the one my assistant told me about.", "potionmakingdressren", true);
        addSound("[1/5] The Assistant: Heeh...stranger. I...may need your assistance...", "potionmakingassistant1", false);
        addSound("[1/5] The Assistant: Good...good. You have the mushrooms.", "potionmakingassistant2", false);
        addSound("[1/3] The Assistant: I...must thank you, stranger...", "potionmakingassistant3", false);

        //Underwater
        addSound("[1/6] Sayrr: Why ya looking at me like that? Is it cause I'm small, eh? I'll have you know that I caught fish that were twice your size!", "underwatersayrr1", false);
        addSound("[1/2] Sayrr: Ah! Welcome back! Any luck with the fish?", "underwatersayrr2", false);
        addSound("[1/9] Omango: Hi, my name is Omango. I am a resident of the Maltic village, on top of the hill.", "underwateromango1", false);
        addSound("[1/2] Omango: You got it! You found the treasure!", "underwateromango2", false);
    }


    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(ModCore.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    private void addSound(String message, String name, boolean movingSound) {
        message = formatToSound(message);
        sounds.put(message, new CustomSoundClass(registerSound(name), movingSound));
    }

    public static String formatToSound(String message) {
        message = message.replaceAll(" ", "");
        message = message.replaceAll("’", "");
        message = message.replaceAll("\\.", "");
        message = message.replaceAll(",", "");
        message = message.replace("…", "");
        message = message.toLowerCase();
        return message;
    }

}
