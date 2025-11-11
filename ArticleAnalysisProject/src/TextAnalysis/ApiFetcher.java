package TextAnalysis;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class ApiFetcher {

    private static final String KEY = "fbcf0024f7514792a78ea28c6e1a71dc";

    public ArrayList<ApiArticle> fetchArticles() throws Exception {

        ArrayList<ApiArticle> results = new ArrayList<>();

        int page = 1 + (int)(Math.random() * 3);

        String urlStr =
            "https://newsapi.org/v2/top-headlines?country=us&pageSize=10&page="
            + page + "&apiKey=" + KEY;

        String json = fetch(urlStr);

        Pattern statusPattern = Pattern.compile("\"status\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
        Matcher statusMatcher = statusPattern.matcher(json);
        String status = "error";
        if (statusMatcher.find()) {
            status = statusMatcher.group(1);
        }

        if (!"ok".equals(status)) {
            Pattern msgP = Pattern.compile("\"message\"\\s*:\\s*\"(.*?)\"", Pattern.DOTALL);
            Matcher msgM = msgP.matcher(json);
            String msg = "";
            if (msgM.find()) {
                msg = unescapeJsonString(msgM.group(1));
            }
            System.out.println("API Error: " + msg);
            return results;
        }
        List<String> titles = extractFields(json, "title");
        List<String> authors = extractFields(json, "author");
        List<String> descriptions = extractFields(json, "description");
        List<String> contents = extractFields(json, "content");

        int n = Math.min(Math.min(titles.size(), authors.size()),
                         Math.min(descriptions.size(), contents.size()));

        for (int i = 0; i < n; i++) {
            results.add(new ApiArticle(
                    clean(titles.get(i)),
                    clean(authors.get(i)),
                    clean(descriptions.get(i)),
                    clean(contents.get(i))
            ));
        }

        return results;
    }

    private String fetch(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "UTF-8")
        );

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }

    private String clean(String s) {
        return (s == null || s.equals("null")) ? "" : s;
    }
    private List<String> extractFields(String json, String fieldName) {
        List<String> results = new ArrayList<>();
        Pattern p = Pattern.compile("\"" + Pattern.quote(fieldName) + "\"\\s*:\\s*(null|\"(.*?)\")", Pattern.DOTALL);
        Matcher m = p.matcher(json);
        while (m.find()) {
            String whole = m.group(1);
            if ("null".equals(whole) || whole == null) {
                results.add("");
            } else {
                String inner = m.group(2);
                if (inner == null) inner = "";
                results.add(unescapeJsonString(inner));
            }
        }
        return results;
    }
    private String unescapeJsonString(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char next = s.charAt(++i);
                switch (next) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    case 'u':
                        if (i + 4 < s.length()) {
                            String hex = s.substring(i+1, i+5);
                            try {
                                int code = Integer.parseInt(hex, 16);
                                sb.append((char) code);
                                i += 4;
                            } catch (NumberFormatException e) {
                            }
                        }
                        break;
                    default: sb.append(next); break;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static class ApiArticle {

        private String title;
        private String author;
        private String description;
        private String content;

        public ApiArticle(String title, String author,
                          String description, String content) {
            this.title = title;
            this.author = author;
            this.description = description;
            this.content = content;
        }

        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getDescription() { return description; }
        public String getContent() { return content; }

        public String fullText() {
            return title + " " + description + " " + content;
        }

        @Override
        public String toString() {
            return fullText();
        }
    }
}
