/*
 * Copyright 2016 Tamir Shomer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.tamir7.contacts;

import android.database.Cursor;
import android.provider.ContactsContract;

class CursorHelper {
    private final Cursor c;

    CursorHelper(Cursor c) {
        this.c = c;
    }

    Long getContactId() {
        return getLong(ContactsContract.RawContacts.CONTACT_ID);
    }

    String getMimeType() {
        return getString(ContactsContract.Data.MIMETYPE);
    }

    String getDisplayName() {
        return getString(ContactsContract.Data.DISPLAY_NAME);
    }

    String getGivenName() {
        return getString(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
    }

    String getFamilyName() {
        return getString(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME);
    }

    String getCompanyName() {
        return getString(ContactsContract.CommonDataKinds.Organization.COMPANY);
    }

    String getCompanyTitle() {
        return getString(ContactsContract.CommonDataKinds.Organization.TITLE);
    }

    String getWebsite() {
        return getString(ContactsContract.CommonDataKinds.Website.URL);
    }

    String getNote() {
        return getString(ContactsContract.CommonDataKinds.Note.NOTE);
    }

    Address getAddress() {
        String address = getString(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS);
        if (address == null) {
            return null;
        }

        Integer typeValue = getInt(ContactsContract.CommonDataKinds.StructuredPostal.TYPE);
        Address.Type type = typeValue == null ? Address.Type.UNKNOWN : Address.Type.fromValue(typeValue);

        String street = getString(ContactsContract.CommonDataKinds.StructuredPostal.STREET);
        String city = getString(ContactsContract.CommonDataKinds.StructuredPostal.CITY);
        String region = getString(ContactsContract.CommonDataKinds.StructuredPostal.REGION);
        String postcode = getString(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE);
        String country = getString(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY);

        if (!type.equals(Address.Type.CUSTOM)) {
            return new Address(address, street, city, region, postcode, country, type);
        }

        String label = getString(ContactsContract.CommonDataKinds.StructuredPostal.LABEL);
        return new Address(address, street, city, region, postcode, country, label);
    }

    PhoneNumber getPhoneNumber() {
        String number = getString(ContactsContract.CommonDataKinds.Phone.NUMBER);
        if (number == null) {
            return null;
        }

        String normalizedNumber = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            normalizedNumber = getString(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
        }

        Integer typeValue = getInt(ContactsContract.CommonDataKinds.Phone.TYPE);
        PhoneNumber.Type type = typeValue == null ? PhoneNumber.Type.UNKNOWN :
                PhoneNumber.Type.fromValue(typeValue);
        if (!type.equals(PhoneNumber.Type.CUSTOM)) {
            return new PhoneNumber(number, type, normalizedNumber);
        }

        return new PhoneNumber(number,
                getString(ContactsContract.CommonDataKinds.Phone.LABEL), normalizedNumber);
    }

    Email getEmail() {
        String address = getString(ContactsContract.CommonDataKinds.Email.ADDRESS);
        if (address == null) {
            return null;
        }

        Integer typeValue = getInt(ContactsContract.CommonDataKinds.Email.TYPE);
        Email.Type type = typeValue == null ? Email.Type.UNKNOWN : Email.Type.fromValue(typeValue);
        if (!type.equals(Email.Type.CUSTOM)) {
            return new Email(address, type);
        }

        return new Email(address, getString(ContactsContract.CommonDataKinds.Email.LABEL));
    }

    String getPhotoUri() {
        return getString(ContactsContract.Data.PHOTO_URI);
    }


    Event getEvent() {
        String startDate = getString(ContactsContract.CommonDataKinds.Event.START_DATE);
        if (startDate == null) {
            return null;
        }

        Integer typeValue = getInt(ContactsContract.CommonDataKinds.Event.TYPE);
        Event.Type type = typeValue == null ? Event.Type.UNKNOWN : Event.Type.fromValue(typeValue);
        if (!type.equals(Event.Type.CUSTOM)) {
            return new Event(startDate, type);
        }

        return new Event(startDate, getString(ContactsContract.CommonDataKinds.Event.LABEL));
    }

    String getString(String column) {
        int index = c.getColumnIndex(column);
        return index == -1 ? null : c.getString(index);
    }

    Integer getInt(String column) {
        int index = c.getColumnIndex(column);
        return index == -1 ? null : c.getInt(index);
    }

    Long getLong(String column) {
        int index = c.getColumnIndex(column);
        return index == -1 ? null : c.getLong(index);
    }
}
