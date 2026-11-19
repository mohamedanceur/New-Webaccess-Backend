
package com.Atos.Webaccess.Approve.Filtre;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FilterImageApprove implements Filter {

    private static final String IMAGE_URL_PREFIX = "virtualApprove/imagesApprove";

    public static String IMAGE_REPOSITORY;

    @Override
    public void init(FilterConfig config) throws ServletException {
        IMAGE_REPOSITORY = config.getInitParameter("IMAGE_REPOSITORY");
    }

    @Override
    public void destroy() {
        // Nothing to release.
    }

    @Override
    public void doFilter(
            jakarta.servlet.ServletRequest request,
            jakarta.servlet.ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        int prefixIndex = requestURI.indexOf(IMAGE_URL_PREFIX);

        // Let other requests continue through the filter chain.
        if (prefixIndex == -1) {
            chain.doFilter(request, response);
            return;
        }

        String imagePath = requestURI.substring(
                prefixIndex + IMAGE_URL_PREFIX.length()
        ).replace("%20", " ");

        File imageFile = new File(imagePath);

        // Check that the requested image exists and is a file.
        if (!imageFile.exists() || !imageFile.isFile() || !imageFile.canRead()) {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = httpRequest.getServletContext()
                .getMimeType(requestURI);

        httpResponse.setContentType(
                mimeType != null ? mimeType : "application/octet-stream"
        );

        httpResponse.setContentLengthLong(imageFile.length());

        try (
                FileInputStream input = new FileInputStream(imageFile);
                ServletOutputStream output = httpResponse.getOutputStream()
        ) {
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.flush();
        }
    }
}
