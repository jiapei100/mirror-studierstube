# use all C++ sources except "robust_pose.cpp":
FILE(GLOB_RECURSE artoolkitplus_SOURCES *.cpp *.cxx)
LIST(REMOVE_ITEM artoolkitplus_SOURCES ${CMAKE_CURRENT_SOURCE_DIR}/librpp/MATLAB/robust_pose.cpp)

INCLUDE_DIRECTORIES(../include)
ADD_LIBRARY(ARToolKitPlus SHARED ${artoolkitplus_SOURCES})
INSTALL(TARGETS ARToolKitPlus DESTINATION ${CMAKE_INSTALL_LIBDIR})
TARGET_LINK_LIBRARIES(ARToolKitPlus ${OPENVIDEO_LIBRARIES})

SET_TARGET_PROPERTIES(ARToolKitPlus PROPERTIES VERSION ${CPACK_PACKAGE_VERSION} SOVERSION ${CPACK_PACKAGE_VERSION_MAJOR})
