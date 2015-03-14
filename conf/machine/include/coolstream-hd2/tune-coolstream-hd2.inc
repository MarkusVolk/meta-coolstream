DEFAULTTUNE ?= "cortexa9hf"

require conf/machine/include/arm/arch-armv7a.inc


TUNEVALID[cortexa9] = "Enable Cortex-A9 specific processor optimizations"
TUNE_CCARGS .= "${@bb.utils.contains("TUNE_FEATURES", "cortexa9", " -mtune=cortex-a9", "", d)}"
TUNE_CCARGS += "${@bb.utils.contains("TUNE_FEATURES", "vfp", "-mfpu=vfpv3-d16-fp16", "", d)}"

# Little Endian base configs
AVAILTUNES += "cortexa9"
ARMPKGARCH_tune-cortexa9 = "cortexa9"
TUNE_FEATURES_tune-cortexa9 = "${TUNE_FEATURES_tune-armv7a} cortexa9"
PACKAGE_EXTRA_ARCHS_tune-cortexa9 = "${PACKAGE_EXTRA_ARCHS_tune-armv7a} cortexa9-vfp"

# VFP Tunes
AVAILTUNES += "cortexa9hf"
ARMPKGARCH_tune-cortexa9hf = "cortexa9"
TUNE_FEATURES_tune-cortexa9hf ?= "${TUNE_FEATURES_tune-armv7ahf} cortexa9"
PACKAGE_EXTRA_ARCHS_tune-cortexa9hf = "${PACKAGE_EXTRA_ARCHS_tune-armv7ahf} cortexa9hf-vfp"
