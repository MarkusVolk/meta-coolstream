require linux.inc

DEPENDS += "u-boot-mkimage-native"

DESCRIPTION = "Linux kernel for the Coolstream HD2 boxes"
COMPATIBLE_MACHINE = "coolstream-hd2"
PROVIDES = "virtual/kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"
SRCREV = "${AUTOREV}"
PV = "${KV}+${SRCPV}"

SRC_URI = " \
	git://git.slknet.de/git/cst-public-linux-kernel.git;branch=cst_3.10;protocol=git \
	file://COPYING.GPL \
	file://${BOXTYPE}_defconfig \
	file://${BOXTYPE}.dtb \
	file://0001-arch-arm-vfp-Makefile-adjust-kbuild_aflags.patch;apply=yes \
	file://0001-change-boot-splash.patch;apply=yes \
"

S = "${WORKDIR}/git"

KERNEL_IMAGEDEST = "${localstatedir}/update"

kernel_do_configure_prepend() {
	# install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
	cp '${WORKDIR}/${BOXTYPE}_defconfig' '${S}/.config'
}

kernel_do_install_prepend() {
	install -d ${D}${localstatedir}/update
	cat arch/arm/boot/zImage ${WORKDIR}/${BOXTYPE}.dtb > arch/arm/boot/zImage_DTB
	uboot-mkimage -A arm -O linux -T kernel -a 0x008000 -e 0x008000 -C none \
		-n "CS HD2 Kernel ${PV} (zImage)" -d arch/arm/boot/zImage_DTB zImage
	# hack: we replace the zImage with the U-Boot image...
	mv arch/arm/boot/zImage arch/arm/boot/zImage.orig
	mv zImage arch/arm/boot/zImage
}

kernel_do_install_append() {
	if [ INCLUDE_KERNEL == "yes" ];then 
	mv ${D}${localstatedir}/update/zImage-${KERNEL_VERSION} ${D}${localstatedir}/update/vmlinux.ub.gz
	else
	rm ${D}${localstatedir}/update/zImage-${KERNEL_VERSION}
	fi
}
	
FILES_kernel-image = "/var/update"

