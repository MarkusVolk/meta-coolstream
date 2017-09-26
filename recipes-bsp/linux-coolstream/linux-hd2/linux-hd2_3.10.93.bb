require linux.inc

DEPENDS += "u-boot-mkimage-native"

DESCRIPTION = "Linux kernel for the Coolstream HD2 boxes"
COMPATIBLE_MACHINE = "coolstream-hd2"
PROVIDES = "virtual/kernel"
PACKAGE_ARCH = "${MACHINE_ARCH}"


SRCREV = "${AUTOREV}"


SRC_URI = " \
	git://github.com/tuxbox-neutrino/linux-kernel-cst.git;branch=cst_3.10.93;protocol=git \
	file://COPYING.GPL \
	file://${BOXTYPE}_defconfig \
	file://${BOXTYPE}.dtb \
	file://0001-arch-arm-vfp-Makefile-adjust-kbuild_aflags.patch;apply=yes \
	file://0001-change-boot-splash.patch;apply=yes \
	file://0001-fix-build-with-gcc-5.1.patch \
"

S = "${WORKDIR}/git"

KERNEL_IMAGEDEST = "${localstatedir}/update"

CFLAGS_append += "-Wno-maybe-uninitialized"

kernel_do_configure_prepend() {
	if [ -f .config ];then
	make mrproper
	fi
	cp '${WORKDIR}/${BOXTYPE}_defconfig' '${S}/.config'
	ln -sf ${S}/include/linux/compiler-gcc5.h ${S}/include/linux/compiler-gcc6.h
}

kernel_do_install_prepend() {
	install -d ${D}${localstatedir}/update
	if [ -e arch/arm/boot/zImage_DTB ];then
		cp arch/arm/boot/zImage.orig arch/arm/boot/zImage
	else
		cp arch/arm/boot/zImage arch/arm/boot/zImage.orig
	fi
	cat arch/arm/boot/zImage ${WORKDIR}/${BOXTYPE}.dtb > arch/arm/boot/zImage_DTB
	uboot-mkimage -A arm -O linux -T kernel -a 0x008000 -e 0x008000 -C none \
		-n "CS HD2 Kernel ${PV} (zImage)" -d arch/arm/boot/zImage_DTB arch/arm/boot/zImage
	if [ ${INCLUDE_KERNEL} = "yes" ];then
	install arch/arm/boot/zImage ${D}${localstatedir}/update/vmlinux.ub.gz
	fi
}

kernel_do_install_append() {
	rm -f ${D}${localstatedir}/update/zImage-${KERNEL_VERSION}
	if [ ! -d ${DEPLOY_DIR_IMAGE}/flashimage ];then
		mkdir -p ${DEPLOY_DIR_IMAGE}/flashimage
	fi
	cp -u arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/flashimage/vmlinux.ub.gz;
}

FILES_kernel-image = "/var/update"
