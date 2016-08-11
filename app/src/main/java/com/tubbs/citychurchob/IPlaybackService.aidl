// IPlaybackService.aidl
package com.tubbs.citychurchob;

//AIDL file: Defines interfaces for communicating between activities and your service.
//You can see these implemented in the service @ 'private static class ServiceStub extends IPlaybackService.Stub'

	interface IPlaybackService {

	    void stop();

	    void play();

	    void pause();

	    boolean openFile(String path);

	    long getDuration();

	    long getPosition();

	    void seek(long pos);

	    boolean isPlaying();

	}