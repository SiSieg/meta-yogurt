SUMMARY = "Machine specific qtbase configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "\
    file://qtLauncher \
    file://eglfs_kms.config \
"

QT_QPA_PLATFORM ??= "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'wayland', 'eglfs', d)}"

do_install() {
	install -d ${D}/${sysconfdir}
	install -m 0644 ${WORKDIR}/eglfs_kms.config ${D}/${sysconfdir}/eglfs_kms.config

	install -d ${D}/${bindir}
	install -m 0755 ${WORKDIR}/qtLauncher ${D}/${bindir}/qtLauncher
	sed -i 's,@QT_QPA_PLATFORM@,${QT_QPA_PLATFORM},g' ${D}/${bindir}/qtLauncher
	sed -i 's,@QT_QPA_EGLFS_KMS_CONFIG@,${sysconfdir}/eglfs_kms.config,g' ${D}/${bindir}/qtLauncher
}
