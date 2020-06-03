# Copyright (C) 2019 Martin Schwan <m.schwan@phytec.de>
# Released under the MIT license (see COPYING.MIT for the terms)

DESCRIPTION = "Preinstallation of Docker images for the AI-kit"
HOMEPAGE = "https://www.phytec.de"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    ftp://ftp.phytec.de/pub/Software/Linux/BSP-Yocto-IoTEdge/aikit-docker-images/${PN}-${PV}.tar.gz;unpack=false \
    file://aikit-docker-images.service \
    file://aikit-docker-images.py \
"
SRC_URI[md5sum] = "4bebe2fdeb81596b8553311aa0f195c5"
SRC_URI[sha256sum] = "3faba2298d7a261a7503c6ceaa2886c951b47f75021c2d781e501ffe999b56d9"

inherit systemd

SYSTEMD_SERVICE_${PN} = "aikit-docker-images.service"

EXCLUDE_FROM_SHLIBS = "1"
INHIBIT_DEFAULT_DEPS = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

DEPENDS = "virtual/fakeroot-native"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

PREFIX = "${localstatedir}/lib"

fakeroot do_install () {
    mkdir -p ${D}${PREFIX}
    tar --no-same-owner -xpf ${WORKDIR}/${PN}-${PV}.tar.gz -C ${D}${PREFIX}
    install -Dm 0644 ${WORKDIR}/aikit-docker-images.service ${D}${systemd_system_unitdir}/aikit-docker-images.service
    install -Dm 0755 ${WORKDIR}/aikit-docker-images.py ${D}${bindir}/aikit-docker-images
}

FILES_${PN} = " \
    ${localstatedir}/lib/docker \
    ${systemd_system_unitdir}/* \
    ${bindir}/aikit-docker-images \
"
RDEPENDS_${PN} = "iotedge-cli iotedge-daemon docker python3"
INSANE_SKIP_${PN} = " \
    already-stripped \
    staticdev \
    dev-so \
    ldflags \
    arch \
    infodir \
    textrel \
    file-rdeps \
"
