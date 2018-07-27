import React from 'react';

const Message = ( props ) => {

    const showSuccess = (summary, messages) => {
        return <div className="message message-success">{summary}
        {
            messages.map(function(item, i) {
                return <li key={i}>{item}</li>
            })
        }
        </div>

    }
    const showErrors = (summary, errors) => {
        return <div className="message message-error">{summary}
        {
            errors.map(function(item, i) {
                return <li key={i}>{item}</li>
            })
        }
        </div>
    }
    if (props.success) {
        return showSuccess(props.summary, props.messages);
    } else {
        return showErrors(props.summary, props.messages);
    }
}

export default Message;