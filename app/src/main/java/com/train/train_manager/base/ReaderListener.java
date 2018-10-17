package com.train.train_manager.base;

import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;

public class ReaderListener implements BarcodeReader.BarcodeListener{

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {

    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }
}
