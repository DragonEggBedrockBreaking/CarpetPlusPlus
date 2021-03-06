import java.nio.file.Path;
import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.quilt.QuiltMaven;
import io.github.coolcrabs.brachyura.quilt.SimpleQuiltProject;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends SimpleQuiltProject {
    @Override
    public VersionMeta createMcVersion() {
        return Minecraft.getVersion(Versions.MINECRAFT_VERSION);
    }

    @Override
    public MappingTree createMappings() {
        return createMojmap();
    }

    @Override
    public FabricLoader getLoader() {
        return new FabricLoader(QuiltMaven.URL, QuiltMaven.loader(Versions.QUILT_LOADER_VERSION));
    }

    @Override
    public void getModDependencies(ModDependencyCollector d) {
        // Carpet
        d.addMaven("https://masa.dy.fi/maven", new MavenId("carpet", "fabric-carpet", Versions.CARPET_VERSION), ModDependencyFlag.COMPILE, ModDependencyFlag.RUNTIME);
        // LazyDFU
        d.addMaven("https://api.modrinth.com/maven/", new MavenId("maven.modrinth", "lazydfu", Versions.LAZYDFU_VERSION), ModDependencyFlag.RUNTIME);
    }

    @Override
    public int getJavaVersion() {
        return Versions.JAVA_VERSION;
    }

    @Override
    public BrachyuraDecompiler decompiler() {
        return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", Versions.QUILTFLOWER_VERSION)));
    }

    @Override
    public Path getBuildJarPath() {
        return getBuildLibsDir().resolve(getModId() + "-" + "mc" + createMcVersion().version + "-" + getVersion() + "-quilt" + ".jar");
    }
}
