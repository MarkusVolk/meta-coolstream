
<<<<<<< HEAD
ASNEEDED = "-Wl,--as-needed"
=======
ASNEEDED = "-Wl -Wno-format-security -Os ,--as-needed"
>>>>>>> parent of 9f38a3f... cleanup

ASNEEDED_pn-console-tools = ""
ASNEEDED_pn-distcc = ""
ASNEEDED_pn-openobex = ""
ASNEEDED_pn-icu = ""
ASNEEDED_pn-xserver-kdrive-xomap = ""
ASNEEDED_pn-minimo = ""
ASNEEDED_pn-pciutils = ""
ASNEEDED_pn-puzzles = ""
ASNEEDED_pn-dialer = ""
ASNEEDED_pn-pulseaudio = ""
ASNEEDED_pn-rpm = ""
ASNEEDED_pn-oscam = "-lrt"

TARGET_LDFLAGS += "${ASNEEDED}"
