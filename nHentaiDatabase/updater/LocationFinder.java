package updater;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;

public class LocationFinder {
	/**
	 * Gets the base location of the given class.
	 * <p>
	 * If the class is directly on the file system (e.g.,
	 * "/path/to/my/package/MyClass.class") then it will return the base directory
	 * (e.g., "file:/path/to").
	 * </p>
	 * <p>
	 * If the class is within a JAR file (e.g.,
	 * "/path/to/my-jar.jar!/my/package/MyClass.class") then it will return the
	 * path to the JAR (e.g., "file:/path/to/my-jar.jar").
	 * </p>
	 *
	 * @param c The class whose location is desired.
	 * @see FileUtils#urlToFile(URL) to convert the result to a {@link File}.
	 */
	public static URL getLocationClass(final Class<?> c) {
	    if (c == null) return null; // could not load the class

	    // try the easy way first
	    try {
	        final URL codeSourceLocation =
	            c.getProtectionDomain().getCodeSource().getLocation();
	        if (codeSourceLocation != null) return codeSourceLocation;
	    }
	    catch (final SecurityException e) {
	        // NB: Cannot access protection domain.
	    }
	    catch (final NullPointerException e) {
	        // NB: Protection domain or code source is null.
	    }

	    // NB: The easy way failed, so we try the hard way. We ask for the class
	    // itself as a resource, then strip the class's path from the URL string,
	    // leaving the base path.

	    // get the class's raw resource path
	    final URL classResource = c.getResource(c.getSimpleName() + ".class");
	    if (classResource == null) return null; // cannot find class resource

	    final String url = classResource.toString();
	    final String suffix = c.getCanonicalName().replace('.', '/') + ".class";
	    if (!url.endsWith(suffix)) return null; // weird URL

	    // strip the class's path from the URL string
	    final String base = url.substring(0, url.length() - suffix.length());

	    String path = base;

	    // remove the "jar:" prefix and "!/" suffix, if present
	    if (path.startsWith("jar:")) path = path.substring(4, path.length() - 2);

	    try {
	        return new URL(path);
	    }
	    catch (final MalformedURLException e) {
	        e.printStackTrace();
	        return null;
	    }
	} 
	
	/**
	 * Returns the Location of the running Jar file
	 * 
	 * @param c The class inside of the jar file
	 * @return String
	 */
	public static String getLocationJar() {
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		File dir = jarDir.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		System.out.println(path);
		
		return path;
	}
	
	/**
	 * Compute the absolute file path to the jar file.
	 * The framework is based on http://stackoverflow.com/a/12733172/1614775
	 * But that gets it right for only one of the four cases.
	 * 
	 * @param aclass A class residing in the required jar.
	 * 
	 * @return A File object for the directory in which the jar file resides.
	 * During testing with NetBeans, the result is ./build/classes/,
	 * which is the directory containing what will be in the jar.
	 */
	public static File getJarDir(Class aclass) {
	    URL url;
	    String extURL;      //  url.toExternalForm();

	    // get an url
	    try {
	        url = aclass.getProtectionDomain().getCodeSource().getLocation();
	          // url is in one of two forms
	          //        ./build/classes/   NetBeans test
	          //        jardir/JarName.jar  froma jar
	    } catch (SecurityException ex) {
	        url = aclass.getResource(aclass.getSimpleName() + ".class");
	        // url is in one of two forms, both ending "/com/physpics/tools/ui/PropNode.class"
	        //          file:/U:/Fred/java/Tools/UI/build/classes
	        //          jar:file:/U:/Fred/java/Tools/UI/dist/UI.jar!
	    }

	    // convert to external form
	    extURL = url.toExternalForm();

	    // prune for various cases
	    if (extURL.endsWith(".jar"))   // from getCodeSource
	        extURL = extURL.substring(0, extURL.lastIndexOf("/"));
	    else {  // from getResource
	        String suffix = "/"+(aclass.getName()).replace(".", "/")+".class";
	        extURL = extURL.replace(suffix, "");
	        if (extURL.startsWith("jar:") && extURL.endsWith(".jar!"))
	            extURL = extURL.substring(4, extURL.lastIndexOf("/"));
	    }

	    // convert back to url
	    try {
	        url = new URL(extURL);
	    } catch (MalformedURLException mux) {
	        // leave url unchanged; probably does not happen
	    }

	    // convert url to File
	    try {
	        return new File(url.toURI());
	    } catch(URISyntaxException ex) {
	        return new File(url.getPath());
	    }
	}
}
