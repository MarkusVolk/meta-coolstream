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
	file://0001-arch-arm-vfp-Makefile-adjust-kbuild_aflags.patch \
"

S = "${WORKDIR}/git"

do_configure_prepend() {
	# install -m 0644 ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig || die "No default configuration for ${MACHINE} / ${KERNEL_DEFCONFIG} available."
	cp '${WORKDIR}/${BOXTYPE}_defconfig' '${S}/.config'
}

do_install_prepend() {
	cat arch/arm/boot/zImage ${WORKDIR}/${BOXTYPE}.dtb > arch/arm/boot/zImage_DTB
	uboot-mkimage -A arm -O linux -T kernel -a 0x008000 -e 0x008000 -C none \
		-n "CS HD2 Kernel ${PV} (zImage)" -d arch/arm/boot/zImage_DTB zImage
	# hack: we replace the zImage with the U-Boot image...
	mv arch/arm/boot/zImage arch/arm/boot/zImage.orig
	mv zImage arch/arm/boot/zImage
	if test -e ${DEPLOY_DIR_IMAGE}; then
		cp  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}
		ln -sf ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/vmlinux.ub.gz
	else
		mkdir -p ${DEPLOY_DIR_IMAGE} && cp arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/vmlinux.ub.gz
		ln -sf ${DEPLOY_DIR_IMAGE}/zImage ${DEPLOY_DIR_IMAGE}/vmlinux.ub.gz
	fi
}
