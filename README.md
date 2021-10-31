# rtf-gen is an open source Java library for RTF (Rich Text Format) file generation #

rtf-gen is a Java library for creating RTF (Rich Text Format) files with a LGPL and MPL open source license.

rtf-gen is branched out of iText-2.1.7. It is smaller and simpler than the original since it is concerned only with RTF generation.
Main differences:
- all PDF features removed
- RTF parsing removed, only RTF generation is supported
- external font support is removed
- only images supported by ImageIO can be used
- code is modernized to Java 8 (use of generics, removed deprecated method calls, many small code style fixes)

RTF API is mostly the same as the original iText-2.1.7 API, but it is not binary compatible - recompilation is required.
Differences in RTF API:
- many FontFactory and Image methods are removed
- PDF features removed: hyphenation, PDF tables, annotations, split characters, separator chunks
- close() methods throw IOException
- logging of errors can be configured with RtfWriter2.getDocumentSettings().setLogger(...)
