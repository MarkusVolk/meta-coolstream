FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "file://disable_locale.patch \
				  file://avoid_redeclaration.patch \
"

