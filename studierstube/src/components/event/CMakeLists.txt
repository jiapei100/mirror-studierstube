FILE(GLOB stbevent_SOURCES *.cpp *.cxx)
INCLUDE_DIRECTORIES(../../../include)

# Coin:
INCLUDE_DIRECTORIES(${COIN_INCLUDE_DIR})
ADD_DEFINITIONS(${COIN_DEFINITIONS})
LINK_DIRECTORIES(${COIN_LINK_DIRECTORIES})

# ACE:
INCLUDE_DIRECTORIES(${ACE_INCLUDE_DIR})
ADD_DEFINITIONS(${ACE_DEFINITIONS})
LINK_DIRECTORIES(${ACE_LINK_DIRECTORIES})

# Boost:
INCLUDE_DIRECTORIES(${BOOST_INCLUDE_DIRS})
LINK_DIRECTORIES(${BOOST_LIBRARY_DIRS})

# opentracker:
INCLUDE_DIRECTORIES(${OPENTRACKER_INCLUDE_DIR})
ADD_DEFINITIONS(${OPENTRACKER_DEFINITIONS})
LINK_DIRECTORIES(${OPENTRACKER_LINK_DIRECTORIES})

ADD_LIBRARY(stbevent SHARED ${stbevent_SOURCES})
TARGET_LINK_LIBRARIES(stbevent ${COIN_LIBRARIES} ${TINY_XML_MOD_LIBRARY} ${ACE_LIBRARIES} ${OPENTRACKER_LIBRARIES})
SET_TARGET_PROPERTIES(stbevent PROPERTIES VERSION ${CPACK_PACKAGE_VERSION} SOVERSION ${CPACK_PACKAGE_VERSION_MAJOR})
INSTALL(TARGETS stbevent DESTINATION ${CMAKE_INSTALL_LIBDIR})
