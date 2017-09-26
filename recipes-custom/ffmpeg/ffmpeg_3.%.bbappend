
EXTRA_OECONF_append_coolstream-hd1 = " \
	--cpu=armv6 \
	--disable-vfp \
"

EXTRA_OECONF_append_coolstream-hd2 = " \
	--cpu=cortex-a9 \
	--enable-vfp \
"


do_configure() {
    # We don't have TARGET_PREFIX-pkgconfig
    sed -i '/pkg_config_default="${cross_prefix}${pkg_config_default}"/d' ${S}/configure
    mkdir -p ${B}
    cd ${B}
    ${S}/configure ${EXTRA_OECONF}
    sed -i -e s:Os:O4:g ${B}/config.h
}

do_install_append() {
    install -m 0644 ${S}/libavfilter/*.h ${D}${includedir}/libavfilter/
}
