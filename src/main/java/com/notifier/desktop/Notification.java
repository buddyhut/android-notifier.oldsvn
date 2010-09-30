/*
 * Android Notifier Desktop is a multiplatform remote notification client for Android devices.
 *
 * Copyright (C) 2010  Leandro Aparecido
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.notifier.desktop;

import com.google.common.base.*;

public class Notification {

	public static final String BATTERY_ICON_PREFIX = "battery";
	public static final String BATTERY_ICON_SUFFIX = ".png";

	private final long deviceId;
	private final long notificationId;
	private final Type type;
	private final String data;
	private final String description;

	public static enum Type {
		RING("Phone is ringing", "ring.png") {
			@Override
			public String toString() {
				return "Ring";
			}
		},
		SMS("Phone received an SMS", "sms.png") {
			@Override
			public String toString() {
				return "SMS";
			}
		},
		MMS("Phone received an MMS", "mms.png") {
			@Override
			public String toString() {
				return "MMS";
			}
		},
		BATTERY("Android battery state", "battery100.png") {
			@Override
			public String toString() {
				return "Battery";
			}
		},
		VOICEMAIL("New voicemail", "voicemail.png") {
			@Override
			public String toString() {
				return "Voicemail";
			}
		},
		PING("Phone sent a ping", "app-icon.png") {
			@Override
			public String toString() {
				return "Ping";
			}
		},
		USER("Third-party", "app-icon.png") {
			@Override
			public String toString() {
				return "Third-party";
			}
		}
		;

		private String title;
		private String iconName;
		
		private Type(String title, String iconName) {
			this.title = title;
			this.iconName = iconName;
		}
		
		public String getTitle() {
			return title;
		}

		public String getIconName() {
			return iconName;
		}
	}

	public Notification(long deviceId, long notificationId, Type type, String data, String description) {
		this.deviceId = deviceId;
		this.notificationId = notificationId;
		this.type = type;
		this.data = data;
		this.description = description;
	}

	public String getTitle(String emptyTitle) {
		if (Notification.Type.USER == type) {
			return data == null ? emptyTitle : data;
		} else {
			return type.getTitle();
		}
	}

	public String getBatteryIconName() {
		int level;
		if (Strings.isNullOrEmpty(data)) {
			level = 100;
		} else {
			level = Integer.parseInt(data);
		}
		
		if (level < 0) {
			level = 100;
		} else {
			int mod = level % 5;
			if (mod != 0) {
				if (mod < 3) {
					level -= mod;
				} else {
					level += 5 - mod;
				}
			}
		}
		
		return BATTERY_ICON_PREFIX + level + BATTERY_ICON_SUFFIX;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public Type getType() {
		return type;
	}

	public String getData() {
		return data;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (deviceId ^ (deviceId >>> 32));
		result = prime * result + (int) (notificationId ^ (notificationId >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notification other = (Notification) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (deviceId != other.deviceId)
			return false;
		if (notificationId != other.notificationId)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("notificationId=%s, type=%s", notificationId, type);
	}
}
