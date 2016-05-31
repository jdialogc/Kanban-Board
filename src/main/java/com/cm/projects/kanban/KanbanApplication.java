package com.cm.projects.kanban;

import java.awt.Desktop;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class KanbanApplication {
    @Value("${spring.datasource.driverClassName}")
	private String databaseDriverClassName;
	 
	@Value("${spring.datasource.url}")
	private String datasourceUrl;
	 
	@Value("${spring.datasource.username}")
	private String databaseUsername;
	 
	@Value("${spring.datasource.password}")
	private String databasePassword;
    @Bean
	public DataSource dataSource() {
	    DriverManagerDataSource ds = new DriverManagerDataSource();
	    ds.setDriverClassName(databaseDriverClassName);
	    ds.setUrl(datasourceUrl);
	    ds.setUsername(databaseUsername);
	    ds.setPassword(databasePassword);
	 
	    return ds;
	}

    public static void main(String[] args) {
        if(!available(8094)){
            launchBrowser();
        }
        SpringApplication.run(KanbanApplication.class, args);
        //JOptionPane.showMessageDialog(null, "Kanban is ready on port 8094");        
        launchBrowser();
    }

    private static boolean available(int port) {
        System.out.println("--------------Testing port " + port);
        Socket s = null;
        try {
            s = new Socket("localhost", port);

            // If the code makes it this far without an exception it means
            // something is using the port and has responded.
            System.out.println("--------------Port " + port + " is not available");
            return false;
        } catch (IOException e) {
            System.out.println("--------------Port " + port + " is available");
            return true;
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    //throw new RuntimeException("You should handle this error.", e);
                }
            }
        }
    }
    
    private static void launchBrowser(){
        String url = "http://localhost:8094";

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                /*
                ==========================Linux ===============================
                Runtime rt = Runtime.getRuntime();
String url = "http://stackoverflow.com";
String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                                 "netscape","opera","links","lynx"};

StringBuffer cmd = new StringBuffer();
for (int i=0; i<browsers.length; i++)
     cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

rt.exec(new String[] { "sh", "-c", cmd.toString() });
                
                 */
                Runtime rt = Runtime.getRuntime();
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
