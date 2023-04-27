package camonspring.config;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

  private final ClassLoader classloader;

  public MyAutoConfigImportSelector(ClassLoader classloader) {
    this.classloader = classloader;
  }

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    List<String> autoConfigs = new ArrayList<>();
    ImportCandidates.load(MyAutoConfiguration.class, classloader)
        .forEach(autoConfigs::add);
    return autoConfigs.toArray(new String[0]);
  }
}
