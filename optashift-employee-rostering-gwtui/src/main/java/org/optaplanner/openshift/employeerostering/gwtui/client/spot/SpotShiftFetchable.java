package org.optaplanner.openshift.employeerostering.gwtui.client.spot;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Provider;

import org.optaplanner.openshift.employeerostering.gwtui.client.spot.SpotDataFetchable;
import org.optaplanner.openshift.employeerostering.gwtui.client.calendar.ShiftData;
import org.optaplanner.openshift.employeerostering.gwtui.client.interfaces.Fetchable;
import org.optaplanner.openshift.employeerostering.gwtui.client.interfaces.Updatable;

public class SpotShiftFetchable implements Fetchable<Collection<ShiftData>> {

    Updatable<Collection<ShiftData>> updatable;
    Provider<Integer> tenantIdProvider;
    SpotDataFetchable spotDataFetcher;

    public SpotShiftFetchable(Provider<Integer> tenantIdProvider) {
        this.tenantIdProvider = tenantIdProvider;
        spotDataFetcher = new SpotDataFetchable(tenantIdProvider);
    }

    @Override
    public void fetchData(Command after) {
        SpotDataFetchable spotDataFetchable = new SpotDataFetchable(tenantIdProvider);
        spotDataFetchable.setUpdatable((c) -> {
            updatable.onUpdate(c.stream().map((s) -> new ShiftData(s.getStartTime(), s.getEndTime(), s.getGroupId())).collect(Collectors.toList()));
            after.execute();
        });
        spotDataFetchable.fetchData(() -> {
        });
    }

    @Override
    public void setUpdatable(Updatable<Collection<ShiftData>> listener) {
        updatable = listener;
    }

}