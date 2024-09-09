package com.artofsolving.jodconverter;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用该类覆盖默认的DocumentFormatRegistry
 * 用于支持docx/pptx/docx格式
 * @author booty
 */
public class BasicDocumentFormatRegistry implements DocumentFormatRegistry {

    private List<DocumentFormat> documentFormats = new ArrayList<>();

    public void addDocumentFormat(DocumentFormat documentFormat) {
        documentFormats.add(documentFormat);
    }

    protected List<DocumentFormat> getDocumentFormats() {
        return documentFormats;
    }

    /**
     * @param extension the file extension
     * @return the DocumentFormat for this extension, or null if the extension
     * is not mapped
     */
    @Override
    public DocumentFormat getFormatByFileExtension(String extension) {
        if (extension == null) {
            return null;
        }

        //将文件名后缀统一转化
        if (extension.contains("doc")) {
            extension = "doc";
        }
        if (extension.contains("ppt")) {
            extension = "ppt";
        }
        if (extension.contains("xls")) {
            extension = "xls";
        }
        String lowerExtension = extension.toLowerCase();
        for (DocumentFormat format : documentFormats) {
            if (format.getFileExtension().equals(lowerExtension)) {
                return format;
            }
        }
        return null;
    }

    @Override
    public DocumentFormat getFormatByMimeType(String mimeType) {
        for (DocumentFormat documentFormat : documentFormats) {
            if (documentFormat.getMimeType().equals(mimeType)) {
                return documentFormat;
            }
        }
        return null;
    }
}
