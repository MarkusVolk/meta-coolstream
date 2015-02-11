# Coolstream HD2 BSP default settings

IMAGE_CLASSES += "usb_image-hd2"

JFFS2_ERASEBLOCK = "${@'0x20000' if BOXMODEL != 'tank' else '0x40000'}"
JFFS2_SUM_EXTRA_ARGS = "-e ${JFFS2_ERASEBLOCK} -n -l"
EXTRA_IMAGECMD_jffs2 = "-e ${JFFS2_ERASEBLOCK} -n -l -U"

