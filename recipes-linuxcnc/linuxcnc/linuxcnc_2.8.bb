DESCRIPTION = "LinuxCNC"
SECTION = "examples" 
LICENSE = "GPLv2" 
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263" 
SECTION="realtime/application"

SRC_URI = "git://github.com/LinuxCNC/LinuxCNC.git;protocol=git;rev=master\
           file://0001-minimal-conf.patch\
          "

S = "${WORKDIR}/git"

inherit autotools binconfig pkgconfig gettext

export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export STAGING_LIBDIR_NATIVE
export STAGING_INCDIR_NATIVE
export STAGING_BINDIR_NATIVE
export ARCHCMD="/bin/echo ${TARGET_ARCH}"

DEPENDS = "readline_5.2 eudev libmodbus libusb gtk+3 dpkg python-native python libxinerama boost glib-2.0 pkgconfig"
# DEPENDS="tcl-native tk-native blt-native bwidget-native python-native tcl tk blt python xenomai pasm-native"

RDEPENDS_${PN} = "boost-dev python-dev"

EXTRA_OECONF = "--host --host-alias 'TurBox'"
EXTRA_OECONF += "--enable-non-distributable=yes"
EXTRA_OECONF += "--with-python=${STAGING_BINDIR_NATIVE}/python-native/python"
# EXTRA_OECONF += "--with-boost-python=${STAGING_BINDIR_NATIVE}/python-native/python"
EXTRA_OECONF += "--with-boost=${STAGING_DIR_TARGET}/usr"

do_configure_prepend() {
  cd ${S}/src
  ./autogen.sh       
}

do_configure() {
  cd ${S}/src
  ./configure ${EXTRA_OECONF}
}

do_compile() {
  cd ${S}/src
  make
}
