/*
 * Copyright (c) 2010 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the GNU Lesser General Public License, Version 2.1. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.gnu.org/licenses/lgpl-2.1.txt. The Original Code is Pentaho 
 * Data Integration.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the GNU Lesser Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 */
package org.pentaho.di.core.logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.HasDatabasesInterface;
import org.w3c.dom.Node;

/**
 * This class describes a logging channel logging table
 * 
 * @author matt
 *
 */
public class ChannelLogTable extends BaseLogTable implements Cloneable, LogTableInterface {

	private static Class<?> PKG = ChannelLogTable.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

	public static final String	XML_TAG	= "channel-log-table";
	
	public enum ID {
		
		ID_BATCH("ID_BATCH"),
		CHANNEL_ID("CHANNEL_ID"),
		LOG_DATE("LOG_DATE"),
		LOGGING_OBJECT_TYPE("LOGGING_OBJECT_TYPE"),
		OBJECT_NAME("OBJECT_NAME"),
		OBJECT_COPY("OBJECT_COPY"),
		REPOSITORY_DIRECTORY("REPOSITORY_DIRECTORY"),
		FILENAME("FILENAME"),
		OBJECT_ID("OBJECT_ID"),
		OBJECT_REVISION("OBJECT_REVISION"),
		PARENT_CHANNEL_ID("PARENT_CHANNEL_ID"),
		ROOT_CHANNEL_ID("ROOT_CHANNEL_ID"),
		;
		
		private String id;
		private ID(String id) {
			this.id = id;
		}

		public String toString() {
			return id;
		}
	}
		
	private ChannelLogTable(VariableSpace space, HasDatabasesInterface databasesInterface) {
		super(space, databasesInterface, null, null, null);
	}
	
	@Override
	public Object clone() {
		try {
			ChannelLogTable table = (ChannelLogTable) super.clone();
			table.fields = new ArrayList<LogTableField>();
			for (LogTableField field : this.fields) {
				table.fields.add((LogTableField) field.clone());
			}
			return table;
		}
		catch(CloneNotSupportedException e) {
			return null;
		}
	}

	public String getXML() {
		StringBuffer retval = new StringBuffer();

		retval.append(XMLHandler.openTag(XML_TAG));
        retval.append(XMLHandler.addTagValue("connection", connectionName)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        retval.append(XMLHandler.addTagValue("schema", schemaName)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append(XMLHandler.addTagValue("table", tableName)); //$NON-NLS-1$ //$NON-NLS-2$
        retval.append(XMLHandler.addTagValue("timeout_days", timeoutInDays)); //$NON-NLS-1$ //$NON-NLS-2$
		retval.append(super.getFieldsXML());
		retval.append(XMLHandler.closeTag(XML_TAG)).append(Const.CR);
		
		return retval.toString();
	}
	
	public void loadXML(Node node, List<DatabaseMeta> databases) {
		connectionName = XMLHandler.getTagValue(node, "connection");
		schemaName = XMLHandler.getTagValue(node, "schema");
		tableName = XMLHandler.getTagValue(node, "table");
		timeoutInDays = XMLHandler.getTagValue(node, "timeout_days");
		
		super.loadFieldsXML(node);
	}

	public static ChannelLogTable getDefault(VariableSpace space, HasDatabasesInterface databasesInterface) {
		ChannelLogTable table = new ChannelLogTable(space, databasesInterface);
				
		table.fields.add( new LogTableField(ID.ID_BATCH.id, true, false, "ID_BATCH", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.IdBatch"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.IdBatch"), ValueMetaInterface.TYPE_INTEGER, 8) );
		table.fields.add( new LogTableField(ID.CHANNEL_ID.id, true, false, "CHANNEL_ID", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ChannelId"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ChannelId"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.LOG_DATE.id, true, false, "LOG_DATE", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.LogDate"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.LogDate"), ValueMetaInterface.TYPE_DATE, -1) );
		table.fields.add( new LogTableField(ID.LOGGING_OBJECT_TYPE.id, true, false, "LOGGING_OBJECT_TYPE", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ObjectType"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ObjectType"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.OBJECT_NAME.id, true, false, "OBJECT_NAME", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ObjectName"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ObjectName"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.OBJECT_COPY.id, true, false, "OBJECT_COPY", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ObjectCopy"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ObjectCopy"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.REPOSITORY_DIRECTORY.id, true, false, "REPOSITORY_DIRECTORY", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.RepositoryDirectory"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.RepositoryDirectory"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.FILENAME.id, true, false, "FILENAME", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.Filename"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.Filename"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.OBJECT_ID.id, true, false, "OBJECT_ID", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ObjectId"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ObjectId"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.OBJECT_REVISION.id, true, false, "OBJECT_REVISION", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ObjectRevision"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ObjectRevision"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.PARENT_CHANNEL_ID.id, true, false, "PARENT_CHANNEL_ID", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.ParentChannelId"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.ParentChannelId"), ValueMetaInterface.TYPE_STRING, 255) );
		table.fields.add( new LogTableField(ID.ROOT_CHANNEL_ID.id, true, false, "ROOT_CHANNEL_ID", BaseMessages.getString(PKG, "ChannelLogTable.FieldName.RootChannelId"), BaseMessages.getString(PKG, "ChannelLogTable.FieldDescription.RootChannelId"), ValueMetaInterface.TYPE_STRING, 255) );
		
		table.findField(ID.LOG_DATE.id).setLogDateField(true);
        table.findField(ID.ID_BATCH.id).setKey(true);

		return table;
	}
		
	/**
	 * This method calculates all the values that are required
	 * @param id the id to use or -1 if no id is needed
	 * @param status the log status to use
	 */
	public RowMetaAndData getLogRecord(LogStatus status, Object subject, Object parent) {
		if (subject==null || subject instanceof LoggingHierarchy) {
			
			LoggingHierarchy loggingHierarchy = (LoggingHierarchy) subject;
			LoggingObjectInterface loggingObject = null;
			if (subject!=null) loggingObject = loggingHierarchy.getLoggingObject();
			
			RowMetaAndData row = new RowMetaAndData();
			
			for (LogTableField field : fields) {
				if (field.isEnabled()) {
					Object value = null;
					if (subject!=null) {
						switch(ID.valueOf(field.getId())){
						
						case ID_BATCH : value = new Long(loggingHierarchy.getBatchId()); break;
						case CHANNEL_ID : value = loggingObject.getLogChannelId(); break;
						case LOG_DATE : value = new Date(); break;
						case LOGGING_OBJECT_TYPE : value = loggingObject.getObjectType().toString(); break;
						case OBJECT_NAME : value = loggingObject.getObjectName(); break;
						case OBJECT_COPY : value = loggingObject.getObjectCopy(); break;
						case REPOSITORY_DIRECTORY : value = loggingObject.getRepositoryDirectory()==null ? null : loggingObject.getRepositoryDirectory().getPath(); break;
						case FILENAME : value = loggingObject.getFilename(); break;
						case OBJECT_ID : value = loggingObject.getObjectId()==null ? null : loggingObject.getObjectId().toString(); break;
						case OBJECT_REVISION : value = loggingObject.getObjectRevision()==null ? null : loggingObject.getObjectRevision().toString(); break;
						case PARENT_CHANNEL_ID : value = loggingObject.getParent() == null ? null : loggingObject.getParent().getLogChannelId(); break;
						case ROOT_CHANNEL_ID : value = loggingHierarchy.getRootChannelId(); break;
						}
						
					}

					row.addValue(field.getFieldName(), field.getDataType(), value);
					row.getRowMeta().getValueMeta(row.size()-1).setLength(field.getLength());
				}
			}
				
			return row;
		}
		else {
			return null;
		}
	}
	
	public String getLogTableCode() {
		return "CHANNEL";
	}

	public String getLogTableType() {
		return BaseMessages.getString(PKG, "ChannelLogTable.Type.Description");
	}

	public String getConnectionNameVariable() {
		return Const.KETTLE_CHANNEL_LOG_DB; // $NON-NLS-1$
	}

	public String getSchemaNameVariable() {
		return Const.KETTLE_CHANNEL_LOG_SCHEMA; // $NON-NLS-1$
	}

	public String getTableNameVariable() {
		return Const.KETTLE_CHANNEL_LOG_TABLE; // $NON-NLS-1$
	}

	public List<RowMetaInterface> getRecommendedIndexes() {
	  List<RowMetaInterface> indexes = new ArrayList<RowMetaInterface>();
  	  return indexes;
	}
	
}
