package com.infumia.t3sl4.facmap.utils.scanner;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.infumia.t3sl4.facmap.utils.console.logging.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;

public class FileScanner {
   private static final PathMatcher CLASS_FILE = create("glob:*.class");
   private static final PathMatcher ARCHIVE = create("glob:*.{jar}");

   public static Set<String> scanFile(File f, Class<?> clazz) {
      URL[] urls;
      try {
         urls = new URL[]{f.toURI().toURL()};
      } catch (MalformedURLException var4) {
         var4.printStackTrace();
         return Sets.newHashSet();
      }

      return scanFile(urls, clazz);
   }

   public static Set<String> scanFile(URL url, Class<?> clazz) {
      return scanFile(new URL[]{url}, clazz);
   }

   public static Set<String> scanFile(URL[] urls, Class<?> clazz) {
      Set<URI> sources = Sets.newHashSet();
      Set<String> plugins = Sets.newHashSet();
      URL[] var4 = urls;
      int var5 = urls.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         URL url = var4[var6];
         if (!url.getProtocol().equals("file")) {
            Log.severe("Skipping unsupported classpath source: " + url);
         } else {
            URI source;
            try {
               source = url.toURI();
            } catch (URISyntaxException var10) {
               continue;
            }

            if (sources.add(source)) {
               scanPath(Paths.get(source), plugins, clazz);
            }
         }
      }

      return plugins;
   }

   private static void scanPath(Path path, Set<String> classes, Class<?> clazz) {
      if (Files.exists(path, new LinkOption[0])) {
         if (Files.isDirectory(path, new LinkOption[0])) {
            scanDirectory(path, classes, clazz);
         } else {
            scanZip(path, classes, clazz);
         }
      }

   }

   private static void scanDirectory(Path dir, final Set<String> classes, final Class<?> clazz) {
      try {
         Files.walkFileTree(dir, ImmutableSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
               if (FileScanner.CLASS_FILE.matches(path.getFileName())) {
                  InputStream in = Files.newInputStream(path);
                  Throwable var4 = null;

                  try {
                     String plugin = FileScanner.findClass(in, clazz);
                     if (plugin != null) {
                        classes.add(plugin);
                     }
                  } catch (Throwable var13) {
                     var4 = var13;
                     throw var13;
                  } finally {
                     if (in != null) {
                        if (var4 != null) {
                           try {
                              in.close();
                           } catch (Throwable var12) {
                              var4.addSuppressed(var12);
                           }
                        } else {
                           in.close();
                        }
                     }

                  }
               }

               return FileVisitResult.CONTINUE;
            }
         });
      } catch (IOException var4) {
         Log.severe("Failed to search classes in " + dir);
      }

   }

   private static void scanZip(Path path, Set<String> classes, Class<?> clazz) {
      if (ARCHIVE.matches(path.getFileName())) {
         try {
            ZipFile zip = new ZipFile(path.toFile());
            Throwable var4 = null;

            try {
               Enumeration entries = zip.entries();

               while(entries.hasMoreElements()) {
                  ZipEntry entry = (ZipEntry)entries.nextElement();
                  if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                     InputStream in = zip.getInputStream(entry);
                     Throwable var8 = null;

                     try {
                        String classname = findClass(in, clazz);
                        if (classname != null) {
                           classes.add(classname);
                        }
                     } catch (Throwable var33) {
                        var8 = var33;
                        throw var33;
                     } finally {
                        if (in != null) {
                           if (var8 != null) {
                              try {
                                 in.close();
                              } catch (Throwable var32) {
                                 var8.addSuppressed(var32);
                              }
                           } else {
                              in.close();
                           }
                        }

                     }
                  }
               }
            } catch (Throwable var35) {
               var4 = var35;
               throw var35;
            } finally {
               if (zip != null) {
                  if (var4 != null) {
                     try {
                        zip.close();
                     } catch (Throwable var31) {
                        var4.addSuppressed(var31);
                     }
                  } else {
                     zip.close();
                  }
               }

            }
         } catch (IOException var37) {
            Log.severe("Failed to search classes in " + path);
         }

      }
   }

   private static String findClass(InputStream in, Class<?> clazz) throws IOException {
      ClassReader reader = new ClassReader(in);
      ClassNode classNode = new ClassNode();
      reader.accept(classNode, 7);
      if (clazz.isAnnotation()) {
         if (classNode.visibleAnnotations != null) {
            List<AnnotationNode> annotations = classNode.visibleAnnotations;
            Iterator var5 = annotations.iterator();

            while(var5.hasNext()) {
               AnnotationNode node = (AnnotationNode)var5.next();
               if (node.desc.substring(1, node.desc.length() - 1).equals(clazz.getCanonicalName().replace(".", "/"))) {
                  return classNode.name.replace('/', '.');
               }
            }
         }
      } else if (classNode.superName != null && classNode.superName.equals(clazz.getCanonicalName().replace(".", "/"))) {
         return classNode.name.replace('/', '.');
      }

      return null;
   }

   public static PathMatcher create(String pattern) {
      return FileSystems.getDefault().getPathMatcher(pattern);
   }
}
