FILE(GLOB_RECURSE stbviewer_qt_SOURCES *.cpp *.cxx)
INCLUDE_DIRECTORIES(../../../include)

# Coin:
INCLUDE_DIRECTORIES(${COIN_INCLUDE_DIR})
ADD_DEFINITIONS(${COIN_DEFINITIONS})
LINK_DIRECTORIES(${COIN_LINK_DIRECTORIES})

# ACE:
INCLUDE_DIRECTORIES(${ACE_INCLUDE_DIR})
ADD_DEFINITIONS(${ACE_DEFINITIONS})
LINK_DIRECTORIES(${ACE_LINK_DIRECTORIES})

# openvideo:
INCLUDE_DIRECTORIES(${OPENVIDEO_INCLUDE_DIR})
LINK_DIRECTORIES(${OPENVIDEO_LINK_DIRECTORIES})

# Qt:
INCLUDE(${QT_USE_FILE})

# SoQt:
INCLUDE_DIRECTORIES(${SOQT_INCLUDE_DIR})
ADD_DEFINITIONS(${SOQT_DEFINITIONS} -DUSE_SOQT)
LINK_DIRECTORIES(${SOQT_LINK_DIRECTORIES})

ADD_LIBRARY(stbviewer_qt SHARED ${stbviewer_qt_SOURCES})
TARGET_LINK_LIBRARIES(stbviewer_qt ${COIN_LIBRARIES} ${ACE_LIBRARIES} ${OPENVIDEO_LIBRARIES} ${QT_LIBRARIES} ${SOQT_LIBRARIES})
SET_TARGET_PROPERTIES(stbviewer_qt PROPERTIES VERSION ${CPACK_PACKAGE_VERSION} SOVERSION ${CPACK_PACKAGE_VERSION_MAJOR})
INSTALL(TARGETS stbviewer_qt DESTINATION ${CMAKE_INSTALL_LIBDIR})
