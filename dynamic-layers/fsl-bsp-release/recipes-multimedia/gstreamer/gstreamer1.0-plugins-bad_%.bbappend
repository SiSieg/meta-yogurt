FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI_append = " file://0001-opencv-allow-compilation-against-4.4.x.patch"