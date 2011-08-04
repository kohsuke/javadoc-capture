package org.kohsuke;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import static javax.tools.StandardLocation.*;

/**
 * @author Kohsuke Kawaguchi
 */
@SupportedAnnotationTypes("org.kohsuke.CaptureJavadoc")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@MetaInfServices(Processor.class)
public class CaptureJavadocProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element e : roundEnv.getElementsAnnotatedWith(CaptureJavadoc.class)) {
            String fqcn = ((TypeElement)e).getQualifiedName().toString();
            String doc = processingEnv.getElementUtils().getDocComment(e);
            if (doc!=null && doc.trim().length()>0) {

                String fileName = fqcn.replace('.', '/') + ".javadoc";
                try {
                    FileObject f = processingEnv.getFiler().createResource(CLASS_OUTPUT, "", fileName);
                    Writer w = new OutputStreamWriter(f.openOutputStream(),"UTF-8");
                    w.write(doc);
                    w.close();
                } catch (IOException x) {
                    processingEnv.getMessager().printMessage(Kind.ERROR, "Failed to generate " + fileName, e);
                    x.printStackTrace();
                }
            }
        }
        return true;
    }
}
