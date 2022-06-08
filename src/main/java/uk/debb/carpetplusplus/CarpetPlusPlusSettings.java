package uk.debb.carpetplusplus;

import static carpet.settings.RuleCategory.FEATURE;
import static carpet.settings.RuleCategory.SURVIVAL;
import carpet.settings.Rule;

public class CarpetPlusPlusSettings {
    public static final String CPP = "carpetplusplus";

    @Rule(
        desc = "Bonemealing ice may form calcite",
        extra = "Because in real life, calcite forms in calcium rich water",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean renewableCalcite = false;

    @Rule(
        desc = "Right clicking magma with blaze powder may form tuff",
        extra = "Because in real life, tuff forms in volcanic eruptions",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean renewableTuff = false;

    @Rule(
        desc = "While walking, husks shed sand items",
        extra = "They must be moving on their own, not being pushed",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean husksShedSand = false;

    @Rule(
        desc = "When hit, husks may drop red sand items",
        extra = "It is always 1 item each hit, damage dealt doesn't matter",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean husksDropRedSand = false;

    @Rule(
        desc = "Dispensing a resource into stone/deepslate/netherrack/blackstone makes the ore/gilded blackstone",
        extra = "This has a 7 in 10 chance of working, and a 3 in 10 chance of just spitting out the resource",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean dispensersMakeOres = false;

    @Rule(
        desc = "Cave spiders walking through string weave cobwebs",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean cobwebWeaving = false;

    @Rule(
        desc = "Iron golems standong on big dripleaves 'crushes' them down to small dripleaves",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean dripleafCrushing = false;

    @Rule(
        desc = "A piglin brute killed by a blaze may drop a piglin banner pattern",
        extra = "There is a 1 in 12 chance of this happening",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean brutesDropBannerPatterns = false;

    @Rule(
        desc = "A piglin brute killed by a wither skeleton may drop a pigstep disc",
        extra = "There is a 1 in 8 chance of this happening",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean brutesDropPigstepDiscs = false;

    @Rule(
        desc = "Right clicking a dragon egg that is on top of magma has a change of 'hatching' it",
        extra = "This removes the block and gives you a dragon head",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean dragonEggHatching = false;

    @Rule(
        desc = "When a warden attacks an entity with a sonic boom, an echo shard may drop next to the entity",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean sonicBoomDropsEchoShard = false;

    @Rule(
        desc = "When a warden emerges from the ground, between 1 and 3 disc fragments appear",
        category = {CPP, FEATURE, SURVIVAL}
    )
    public static boolean emergingWardenDropsDiscFragments = false;
}