import React from 'react';
import {
    NativeModules,
    NativeEventEmitter
} from 'react-native';

type singeModule = {
    [key: string]: unknown
};

class Bridge {
    [key: string]: any;

    private eventListener: NativeEventEmitter;
    private NativeModules: singeModule = NativeModules;

    constructor() {
        this.eventListener = new NativeEventEmitter(NativeModules.ToastExample);
    }

    public on(eventType: string, listener: (event: any) => void,) {
        this.eventListener.addListener(eventType, listener);
        return this;
    }

    /**
     * overload emit function
     */
    emit(eventType: string): Promise<any> | void
    emit(eventType: string, params?: {}): Promise<any> | void {
        if (!this[eventType]) {
            return Promise.reject('Function is not defined');
        }

        return this[eventType](params);
    }

    removeListeners(eventType: string): void {
        this.eventListener.removeAllListeners(eventType);
    }

    toast(params?: string): void {
        if (!params) {
            return;
        }
        (this.NativeModules.Toast as any).show(params);
    }
}

export default new Bridge();
