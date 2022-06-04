# CarpetPlusPlus

## Description

This is a Quilt mod that is an extension of the Fabric Carpet mod.
The majority of items in Minecraft are either farmable in some way,
craftable, or tradable with the many types of villagers.
However, a few are not. The [Fabric Carpet mod](https://github.com/gnembon/fabric-carpet) makes some of these farmable:

```
Dead Coral Blocks (renewableCoral)
Cobbled Deepslate (renewableDeepslate)
Coral Blocks      (renewableCoral)
Dead Bush         (desertShrubs)
Deepslate         (renewableDeepslate)
Sponge            (renewableSponges)
```

In addition to these, the [Carpet Extra mod](https://github.com/gnembon/carpet-extra) makes some more of these farmable:

```
Netherrack (renewableNetherrack)
Sand       (renewableSand)
```

And finally, the [Carpet TIS Additions mod](https://github.com/TISUnion/Carpet-TIS-Addition) makes even more farmable:

```
Dragon Egg  (renewableDragonEgg)
Dragon Head (renewableDragonHead)
Elytra      (renewableElytra)
```

I collected together a list of blocks that cannot be obtained renewably,
through crafting, or through villagers, and made this mod to make many of them farmable.
I was also unsatisfied with a couple of the aforementioned farming methods, so I wrote my own.

My mod still does NOT make the following renewable since they are supposed to be very rare and valuable items:

```
Ancient Debris
Diamond
Enchanted Golden Apple
Heart of the Sea
```

## Here are the rules:

### renewableCalcite

Bonemealing ice has a 1 in 5 chance of converting it to calcite.
Bonemeal is consumed even if the block is not converted.
Calcite is formed in calcium rich water,
so using bones (calcium rich) on ice (frozen water) seems appropriate.

### renewableTuff

Right clicking magma with blaze powder has a 1 in 5 chance of converting it to tuff.
The blaze powder is consumed even if the block is not converted.
Tuff is formed in volcanic eruptions.
so using blaze powder (fire-like) on magma (in volcanoes) seems appropriate.

### husksShedSand

Husks that walk in deserts and mesas pick up sand,
so they should drop it sometimes while walking.
This is restricted to only work when they are standing on sand or red sand,
to guarantee that they are walking in desert/mesa terrain.
This works best with Fabric Carpet's `huskSpawningInTemples` rule.

### husksDropRedSand

Husks that walk in deserts and mesas pick up sand,
so they should drop it each time they take damage.
This works best with Fabric Carpet's `huskSpawningInTemples` rule.
This also allows for interesting farms, since we want to quickly kill them,
but we also want them to take small amounts of damage many times
(in a short space of time) to drop more red sand.

### dispensersMakeOres

A dispenser containing a resource (e.g. lapis) pointing at an encaser (e.g. deepslate)
turns the encaser into an ore (e.g. deepslate lapis ore).
This works with both ingots and raw resources, with copper/iron/gold,
which also makes the raw resource renewable,
since you can make ores with iron and break it to get raw iron.
Nether gold ore and gilded blackstone use golden nuggets as the resource.
There is a 3 in 10 chance of this failing and the resource being spat out.

### cobwebWeaving

Cave spiders that walk through string turn the string into cobwebs.
This works when moving both horizontally and vertically.

### dripleafCrushing

Iron golems standing on big dripleaves "crushes" them down to small dripleaves.
Althrough small dripleaves are obtainable through wandering traders,
the traders are not breedable, so that would depend on a rare event and doesn't count.

### brutesDropBannerPatterns

If a piglin brute is killed by a blaze,
it has a 1 in 12 chance of dropping piglin banner pattern.
This farm would require the blaze to target a different mob,
since blazes do not attack piglin brutes on their own.
This farm would also require connecting together a fortress and a bastion.
It works best with Fabric Carpet's `piglinsSpawningInBastions` rule.

### brutesDropPigstepDiscs

If a piglin brute is killed by a wither skeleton,
it has a 1 in 8 chance of dropping a pigstep disk.
Althrough wither skeletons target piglin brutes,
they are significantly overpowered, so you need many wither skeletons,
or you need to stop the brutes from being able to attack.
This farm would also require connecting together a fortress and a bastion.
It works best with Fabric Carpet's `piglinsSpawningInBastions` rule.

### dragonEggHatching

Dragon eggs have a 3 in 11 chance of hatching when right clicked,
if they are 'heated up' by being placed on top of a magma block,
which replaces the dragon egg with a dragon head.
This works well together with the renewable dragon eggs from Carpet TIS Addition.
I think that Carpet TIS Addition's own renewable dragon heads feature is unreasonable.

## Downloads

You can download both stable releases of the mod, and also beta releases, from [Github Releases](https://github.com/DragonEggBedrockBreaking/CarpetPlusPlus/releases).
You can also download stable releases from [Modrinth](https://modrinth.com/mod/carpetplusplus).

## License

This mod is available under the [MPL](LICENSE.txt) license.
